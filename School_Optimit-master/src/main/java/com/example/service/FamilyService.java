package com.example.service;

import com.example.entity.Branch;
import com.example.entity.Family;
import com.example.entity.Student;
import com.example.exception.RecordNotFoundException;
import com.example.exception.UserAlreadyExistException;
import com.example.exception.UserNotFoundException;
import com.example.model.common.ApiResponse;
import com.example.model.request.FamilyAddStudentDto;
import com.example.model.request.FamilyLoginDto;
import com.example.model.response.FamilyResponse;
import com.example.model.response.FamilyResponseList;
import com.example.model.response.StudentResponseDto;
import com.example.repository.BranchRepository;
import com.example.repository.FamilyRepository;
import com.example.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

import static com.example.enums.Constants.*;

@RequiredArgsConstructor
@Service
public class FamilyService implements BaseService<Family, Integer> {

    private final FamilyRepository familyRepository;
    private final StudentRepository studentRepository;
    private final BranchRepository branchRepository;

    @Override
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse create(Family family) {
        Branch branch = branchRepository.findById(family.getComingBranchId()).orElseThrow(() -> new RecordNotFoundException(BRANCH_NOT_FOUND));
        if (familyRepository.existsByPhoneNumber(family.getPhoneNumber())) {
            throw new UserAlreadyExistException(PHONE_NUMBER_ALREADY_REGISTERED);
        }
        Family family1 = Family.from(family);
        Student student = studentRepository.findById(family.getStudentId())
                .orElseThrow(() -> new UserNotFoundException(STUDENT_NOT_FOUND));
        student.getFamily().add(family1);
        family1.setBranch(branch);
        familyRepository.save(family1);
        return new ApiResponse(SUCCESSFULLY, true);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getById(Integer integer) {
        Family family = familyRepository.findById(integer)
                .orElseThrow(() -> new UserNotFoundException(FAMILY_NOT_FOUND));
        return new ApiResponse(FamilyResponse.from(family), true);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse update(Family family) {
        Family family1 = familyRepository.findById(family.getId())
                .orElseThrow(() -> new UserNotFoundException(FAMILY_NOT_FOUND));
        family1.setGender(family.getGender());
        family1.setPassword(family.getPassword());
        family1.setFullName(family.getFullName());
        familyRepository.save(family1);
        return new ApiResponse(SUCCESSFULLY, true);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse delete(Integer integer) {
        Family family = familyRepository.findById(integer)
                .orElseThrow(() -> new UserNotFoundException(FAMILY_NOT_FOUND));
        family.setActive(false);
        familyRepository.save(family);
        return new ApiResponse(DELETED, true);
    }


    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getList(int page, int size, int branchId) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Family> familyList = familyRepository.findAllByBranchIdAndActiveTrue(branchId, pageable);
        List<FamilyResponse> familyResponses = new ArrayList<>();
        familyList.getContent().forEach(obj -> {
            familyResponses.add(FamilyResponse.from(obj));
        });
        return new ApiResponse(new FamilyResponseList(familyResponses, familyList.getTotalElements(), familyList.getTotalPages(), familyList.getNumber()), true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse familyLogIn(FamilyLoginDto dto) {
        Family family = familyRepository.findByPhoneNumberAndPassword(dto.getPhoneNumber(), dto.getPassword())
                .orElseThrow(() -> new UserNotFoundException(FAMILY_NOT_FOUND));
        List<Student> allByFamily = studentRepository.findByFamilyIn(List.of(family));
        List<StudentResponseDto> studentResponseDtoList = new ArrayList<>();
        allByFamily.forEach(student ->
                studentResponseDtoList.add(StudentResponseDto.from(student)));
        return new ApiResponse(studentResponseDtoList, true);
    }

    public ApiResponse add(FamilyAddStudentDto family) {
        Student student = studentRepository.findById(family.getStudentId())
                .orElseThrow(() -> new UserNotFoundException(STUDENT_NOT_FOUND));
        Family family1 = familyRepository.findById(family.getFamilyId()).orElseThrow(() -> new UserNotFoundException(FAMILY_NOT_FOUND));
        student.getFamily().add(family1);
        studentRepository.save(student);
        return new ApiResponse(SUCCESSFULLY, true);
    }
}
