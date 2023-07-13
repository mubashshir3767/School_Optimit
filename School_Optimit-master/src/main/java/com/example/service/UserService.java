package com.example.service;

import com.example.config.jwtConfig.JwtGenerate;
import com.example.entity.Attachment;
import com.example.entity.User;
import com.example.enums.Constants;
import com.example.exception.RecordAlreadyExistException;
import com.example.exception.RecordNotFoundException;
import com.example.exception.UserNotFoundException;
import com.example.model.common.ApiResponse;
import com.example.model.request.*;
import com.example.model.response.NotificationMessageResponse;
import com.example.model.response.TokenResponse;
import com.example.model.response.UserResponseDto;
import com.example.model.response.UserResponseListForAdmin;
import com.example.repository.BranchRepository;
import com.example.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static com.example.enums.Constants.*;


@Service
@RequiredArgsConstructor
public class UserService implements BaseService<UserRegisterDto, Integer> {

    private final UserRepository userRepository;
    private final AttachmentService attachmentService;
    private final SubjectService subjectService;
    private final DailyLessonsService dailyLessonsService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final SmsService service;
    private final FireBaseMessagingService fireBaseMessagingService;
    private final RoleService roleService;
    private final BranchRepository branchRepository;


    @ResponseStatus(HttpStatus.CREATED)
    @Transactional(rollbackFor = {Exception.class})
    @Override
    public ApiResponse create(UserRegisterDto userRegisterDto) {
        checkUserExistByPhoneNumber(userRegisterDto.getPhoneNumber());
        Integer verificationCode = verificationCodeGenerator();
//        sendSms(userRegisterDto.getPhoneNumber(), verificationCode);
        User user = toUser(userRegisterDto, verificationCode);
//        user.setProfilePhoto(attachmentService.saveToSystem(userRegisterDto.getProfilePhoto()));
        userRepository.save(user);
        return new ApiResponse(SUCCESSFULLY, true);
    }

    @Override
    public ApiResponse getById(Integer id) {
        User user = checkUserExistById(id);
        return new ApiResponse(toUserResponse(user), true);
    }

    @Override
    public ApiResponse update(UserRegisterDto userRegisterDto) {
        User user = checkUserExistById(userRegisterDto.getId());
        toUser(userRegisterDto, user.getVerificationCode());
        setPhotoIfIsExist(userRegisterDto, user);
        userRepository.save(user);
        return new ApiResponse(SUCCESSFULLY, true);
    }

    private void setPhotoIfIsExist(UserRegisterDto userRegisterDto, User user) {
        if (userRegisterDto.getProfilePhoto() != null) {
            Attachment attachment = attachmentService.saveToSystem(userRegisterDto.getProfilePhoto());
            if (user.getProfilePhoto() != null) {
                attachmentService.deleteNewName(user.getProfilePhoto());
            }
            user.setProfilePhoto(attachment);
        }
    }

    @Override
    public ApiResponse delete(Integer integer) {
        User optional = checkUserExistById(integer);
        optional.setBlocked(true);
        userRepository.save(optional);
        return new ApiResponse(DELETED, true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse verify(UserVerifyDto userVerifyRequestDto) {
        User user = userRepository.findByPhoneNumberAndVerificationCode(userVerifyRequestDto.getPhoneNumber(), userVerifyRequestDto.getVerificationCode())
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
        user.setVerificationCode(0);
        user.setBlocked(true);
        userRepository.save(user);
        return new ApiResponse(USER_VERIFIED_SUCCESSFULLY, true, new TokenResponse(JwtGenerate.generateAccessToken(user), toUserResponse(user)));
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse login(UserDto userLoginRequestDto) {
        try {
            Authentication authentication = new UsernamePasswordAuthenticationToken(userLoginRequestDto.getPhoneNumber(), userLoginRequestDto.getPassword());
            Authentication authenticate = authenticationManager.authenticate(authentication);
            User user = (User) authenticate.getPrincipal();
            return new ApiResponse(new TokenResponse(JwtGenerate.generateAccessToken(user), toUserResponse(user)), true);
        } catch (BadCredentialsException e) {
            throw new UserNotFoundException(USER_NOT_FOUND);
        }
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse forgetPassword(String number) {
        User user = checkByNumber(number);
        sendSms(checkByNumber(number).getPhoneNumber(), verificationCodeGenerator());
        return new ApiResponse(SUCCESSFULLY, true, user);
    }


    @ResponseStatus(HttpStatus.OK)
    @Transactional(rollbackFor = {Exception.class})
    public ApiResponse addBlockUserByID(Integer id) {
        User user = checkUserExistById(id);
        user.setBlocked(false);
        userRepository.save(user);
        sendNotificationByToken(user, BLOCKED);
        return new ApiResponse(BLOCKED, true);
    }

    @ResponseStatus(HttpStatus.OK)
    @Transactional(rollbackFor = {Exception.class})
    public ApiResponse openToBlockUserByID(Integer id) {
        User user = checkUserExistById(id);
        user.setBlocked(true);
        userRepository.save(user);
        sendNotificationByToken(user, OPEN);
        return new ApiResponse(OPEN, true);
    }


    @ResponseStatus(HttpStatus.OK)
    public ApiResponse saveFireBaseToken(FireBaseTokenRegisterDto fireBaseTokenRegisterDto) {
        User user = checkUserExistById(fireBaseTokenRegisterDto.getUserId());
        user.setFireBaseToken(fireBaseTokenRegisterDto.getFireBaseToken());
        userRepository.save(user);
        return new ApiResponse(SUCCESSFULLY, true);
    }


    @ResponseStatus(HttpStatus.OK)
    public ApiResponse changePassword(String number, String password) {
        User user = checkByNumber(number);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
        return new ApiResponse(user, true);
    }


    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getUserList(int page, int size) {
        Page<User> all = userRepository.findAll(PageRequest.of(page, size));
        List<UserResponseDto> userResponseDtoList = new ArrayList<>();
        all.forEach(obj -> userResponseDtoList.add(toUserResponse(obj)));
        return new ApiResponse(new UserResponseListForAdmin(userResponseDtoList, all.getTotalElements(), all.getTotalPages(), all.getNumber()), true);
    }


    @ResponseStatus(HttpStatus.OK)
    public ApiResponse checkUserResponseExistById() {
        User user = getUserFromContext();
        return new ApiResponse(toUserResponse(checkByNumber(user.getPhoneNumber())), true);
    }


    @ResponseStatus(HttpStatus.OK)
    public ApiResponse removeUserFromContext() {
        User user = checkUserExistByContext();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getName().equals(user.getPhoneNumber())) {
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        return new ApiResponse(DELETED, true);
    }


    public User checkUserExistByContext() {
        User user = getUserFromContext();
        return checkByNumber(user.getPhoneNumber());
    }


    private User getUserFromContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            throw new UserNotFoundException(USER_NOT_FOUND);
        }
        return (User) authentication.getPrincipal();
    }

    public ApiResponse addSubjectToUser(UserRegisterDto userRegisterDto) {
        User user = checkUserExistById(userRegisterDto.getId());
        user.setSubjects(subjectService.checkAllById(userRegisterDto.getSubjectsIds()));
        userRepository.save(user);
        return new ApiResponse(SUCCESSFULLY, true, user.getSubjects());
    }

    public ApiResponse addDailyLessonToUser(UserRegisterDto userRegisterDto) {
        User user = checkUserExistById(userRegisterDto.getId());
        user.setDailyLessons(dailyLessonsService.checkAllById(userRegisterDto.getDailyLessonsIds()));
        userRepository.save(user);
        return new ApiResponse(SUCCESSFULLY, true, user.getDailyLessons());
    }


    private User toUser(UserRegisterDto userRegisterDto, int verificationCode) {
        User user = User.from(userRegisterDto);
        user.setVerificationCode(verificationCode);
        user.setBranch(branchRepository.findById(userRegisterDto.getBranchId()).orElseThrow(()->new RecordNotFoundException(BRANCH_NOT_FOUND)));
        user.setBirthDate(toLocalDate(userRegisterDto.getBirthDate()));
        user.setRoles(roleService.getAllByIds(userRegisterDto.getRolesIds()));
        user.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));
        return user;
    }

    public UserResponseDto toUserResponse(User user) {
        UserResponseDto userResponseDto = UserResponseDto.from(user);
        userResponseDto.setProfilePhotoUrl(getPhotoLink(user.getProfilePhoto()));
        return userResponseDto;
    }

    private String getPhotoLink(Attachment attachment) {
        String photoLink = "https://sb.kaleidousercontent.com/67418/992x558/7632960ff9/people.png";
        if (attachment != null) {
            photoLink = attachmentService.attachUploadFolder + attachment.getPath() + "/" + attachment.getNewName() + "." + attachment.getType();
        }
        return photoLink;
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse reSendSms(String number) {
        sendSms(number, verificationCodeGenerator());
        return new ApiResponse(SUCCESSFULLY, true);
    }


    private Integer verificationCodeGenerator() {
        Random random = new Random();
        return random.nextInt(1000, 9999);
    }

    public User checkUserExistById(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
    }

    private User checkByNumber(String number) {
        return userRepository.findByPhoneNumber(number).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
    }


    private void sendNotificationByToken(User user, String message) {
        NotificationMessageResponse notificationMessageResponse = NotificationMessageResponse.from(user.getFireBaseToken(), message, new HashMap<>());
        fireBaseMessagingService.sendNotificationByToken(notificationMessageResponse);
    }


    private LocalDate toLocalDate(String date) {
        try {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return LocalDate.parse(date, dateTimeFormatter);
        } catch (Exception e) {
            throw new RecordNotFoundException(Constants.DATE_DO_NOT_MATCH + "  " + e);
        }
    }


    private void sendSms(String phoneNumber, Integer verificationCode) {
        service.sendSms(SmsModel.builder()
                .mobile_phone(phoneNumber)
                .message("Cambridge school " + verificationCode + ".")
                .from(4546)
                .callback_url("http://0000.uz/test.php")
                .build());
    }

    private void checkUserExistByPhoneNumber(String phoneNumber) {
        if (userRepository.existsByPhoneNumber(phoneNumber)) {
            throw new RecordAlreadyExistException(USER_ALREADY_EXIST);
        }
    }
}


