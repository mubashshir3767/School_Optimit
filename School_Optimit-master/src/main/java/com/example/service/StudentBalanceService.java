package com.example.service;

import com.example.entity.Balance;
import com.example.entity.Branch;
import com.example.entity.Student;
import com.example.entity.StudentBalance;
import com.example.exception.RecordAlreadyExistException;
import com.example.exception.RecordNotFoundException;
import com.example.exception.UserNotFoundException;
import com.example.model.common.ApiResponse;
import com.example.model.request.StudentAccountDto;
import com.example.model.response.StudentBalanceResponse;
import com.example.repository.BalanceRepository;
import com.example.repository.BranchRepository;
import com.example.repository.StudentBalanceRepository;
import com.example.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.enums.Constants.*;

@Service
@RequiredArgsConstructor
public class StudentBalanceService implements BaseService<StudentAccountDto, Integer> {

    private final StudentBalanceRepository studentBalanceRepository;
    private final StudentRepository studentRepository;
    private final BranchRepository branchRepository;
    private final BalanceRepository balanceRepository;


    @Override
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse create(StudentAccountDto dto) {
        Optional<StudentBalance> byStudentId = studentBalanceRepository.findByStudentId(dto.getStudentId());
        if (byStudentId.isPresent()) {
            throw new RecordAlreadyExistException(ACCOUNT_ALREADY_EXIST);
        }
        Student student = studentRepository.findById(dto.getStudentId()).orElseThrow(() -> new UserNotFoundException(STUDENT_NOT_FOUND));
        Branch branch = branchRepository.findById(dto.getBranchId()).orElseThrow(() -> new RecordNotFoundException(BRANCH_NOT_FOUND));
        Balance balance = balanceRepository.findByBranchId(dto.getBranchId()).orElseThrow(() -> new RecordNotFoundException(BALANCE_NOT_FOUND));
        StudentBalance studentBalance = StudentBalance.builder()
                .balance(dto.getBalance())
                .createdDate(LocalDateTime.now())
                .active(true)
                .branch(branch)
                .student(student)
                .build();
        studentBalanceRepository.save(studentBalance);
        if (dto.getBalance() > 0) {
            balance.setBalance(balance.getBalance() + dto.getBalance());
            balanceRepository.save(balance);
        }
        return new ApiResponse(SUCCESSFULLY, true);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getById(Integer integer) {
        StudentBalance studentBalance = studentBalanceRepository.findById(integer).orElseThrow(() -> new RecordNotFoundException(STUDENT_BALANCE_NOT_FOUND));
        return new ApiResponse(StudentBalanceResponse.from(studentBalance), true);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse update(StudentAccountDto dto) {
        StudentBalance studentBalance = studentBalanceRepository.findById(dto.getId()).orElseThrow(() -> new RecordNotFoundException(STUDENT_BALANCE_NOT_FOUND));
        Balance balance = balanceRepository.findByBranchId(dto.getBranchId()).orElseThrow(() -> new RecordNotFoundException(BALANCE_NOT_FOUND));
        studentBalance.setBalance(studentBalance.getBalance() + dto.getBalance());
        studentBalance.setUpdatedDate(LocalDateTime.now());
        balance.setBalance(balance.getBalance() + dto.getBalance());
        balanceRepository.save(balance);
        studentBalanceRepository.save(studentBalance);
        return new ApiResponse(SUCCESSFULLY, true);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse delete(Integer integer) {
        StudentBalance studentBalance = studentBalanceRepository.findById(integer).orElseThrow(() -> new RecordNotFoundException(STUDENT_BALANCE_NOT_FOUND));
        studentBalance.setActive(false);
        return new ApiResponse(DELETED, true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getByBranchId(Integer integer) {
        List<StudentBalance> reasonList = studentBalanceRepository.findAllByBranchIdAndActiveTrueOrderByCreatedDateAsc(integer);
        List<StudentBalanceResponse> reasonResponseList = new ArrayList<>();
        reasonList.forEach(account -> reasonResponseList.add(StudentBalanceResponse.from(account)));
        return new ApiResponse(reasonResponseList, true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getByStudentId(Integer integer) {
        StudentBalance account = studentBalanceRepository.findByStudentIdAndActiveTrue(integer)
                .orElseThrow(() -> new RecordNotFoundException(STUDENT_BALANCE_NOT_FOUND));
        return new ApiResponse(StudentBalanceResponse.from(account), true);
    }
}
