package com.example.service;

import com.example.entity.StaffAttendance;
import com.example.entity.User;
import com.example.enums.Constants;
import com.example.exception.RecordAlreadyExistException;
import com.example.exception.RecordNotFoundException;
import com.example.exception.UserNotFoundException;
import com.example.model.common.ApiResponse;
import com.example.model.request.StaffAttendanceRequest;
import com.example.model.response.StaffAttendanceResponse;
import com.example.model.response.UserResponseDto;
import com.example.repository.StaffAttendanceRepository;
import com.example.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StaffAttendanceService implements BaseService<StaffAttendanceRequest, Integer> {

    private final StaffAttendanceRepository attendanceRepository;
    private final UserRepository userRepository;


    @Override
    public ApiResponse create(StaffAttendanceRequest staffAttendanceRequest) {
        StaffAttendance staffAttendance = StaffAttendance.toStaffAttendance(staffAttendanceRequest);
        staffAttendance.setUser(getUser(staffAttendanceRequest.getUserId()));
        staffAttendance.setDate(toLocalDate(staffAttendanceRequest.getDate()));
        getByUserId(staffAttendanceRequest.getUserId(),toLocalDate(staffAttendanceRequest.getDate()));
        attendanceRepository.save(staffAttendance);
        return new ApiResponse(Constants.SUCCESSFULLY, true, toDto(staffAttendance));
    }

    @Override
    public ApiResponse getById(Integer integer) {
        return new ApiResponse(Constants.FOUND, true, checkById(integer));
    }

    public ApiResponse getAll() {
        List<StaffAttendanceResponse> all = new ArrayList<>();
        attendanceRepository.findAll().forEach(staffAttendance -> {
            all.add(toDto(staffAttendance));
        });
        return new ApiResponse(Constants.FOUND, true, all);
    }

    @Override
    public ApiResponse update(StaffAttendanceRequest staffAttendanceRequest) {
        checkById(staffAttendanceRequest.getId());
        StaffAttendance staffAttendance = StaffAttendance.toStaffAttendance(staffAttendanceRequest);
        staffAttendance.setId(staffAttendanceRequest.getId());
        staffAttendance.setDate(toLocalDate(staffAttendanceRequest.getDate()));
        staffAttendance.setUser(getUser(staffAttendanceRequest.getUserId()));
        attendanceRepository.save(staffAttendance);
        return new ApiResponse(Constants.SUCCESSFULLY, true, toDto(staffAttendance));
    }

    @Override
    public ApiResponse delete(Integer integer) {
        StaffAttendance staffAttendance = checkById(integer);
        attendanceRepository.deleteById(integer);
        return new ApiResponse(Constants.DELETED, true, staffAttendance);
    }

    public StaffAttendance checkById(Integer integer) {
        return attendanceRepository.findById(integer).orElseThrow(() -> new RecordNotFoundException(Constants.STAFF_ATTENDANCE_NOT_FOUND));
    }

    public StaffAttendanceResponse toDto(StaffAttendance staffAttendance) {
        return StaffAttendanceResponse
                .builder()
                .id(staffAttendance.getId())
                .cameToWork(staffAttendance.isCameToWork())
                .date(staffAttendance.getDate().toString())
                .description(staffAttendance.getDescription())
                .userResponseDto(UserResponseDto.from(staffAttendance.getUser()))
                .build();
    }

    public List<StaffAttendance> findAllByUserAndDateBetween(LocalDate fromDate1, LocalDate toDate1, User user) {
        return attendanceRepository.findAllByUserAndDateBetweenAndCameToWorkTrue(user,fromDate1, toDate1);
    }

    private LocalDate toLocalDate(String date) {
        try {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return LocalDate.parse(date, dateTimeFormatter);
        } catch (Exception e) {
            throw new RecordNotFoundException(Constants.DATE_DO_NOT_MATCH + "  " + e);
        }
    }

    private User getUser(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(Constants.USER_NOT_FOUND));
    }

    private void getByUserId(Integer userId,LocalDate date) {
        if (attendanceRepository.findByUserIdAndDate(userId,date).isPresent()) {
            throw new RecordAlreadyExistException(Constants.CAN_BE_ADDED_ONCE_A_DAY);
        }
    }
}