package com.example.service;

import com.example.entity.*;
import com.example.exception.RecordNotFoundException;
import com.example.exception.UserNotFoundException;
import com.example.model.common.ApiResponse;
import com.example.model.request.ExpenseRequestDto;
import com.example.model.response.ExpenseResponse;
import com.example.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.example.enums.Constants.*;

@Service
@RequiredArgsConstructor
public class ExpenseForStudentService implements BaseService<ExpenseRequestDto, Integer> {

    private final BalanceRepository balanceRepository;
    private final StudentRepository studentRepository;
    private final BranchRepository branchRepository;
    private final ExpenseForStudentRepository expenseForStudentRepository;
    private final PaymentTypeRepository paymentTypeRepository;


    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public ApiResponse create(ExpenseRequestDto dto) {
        Branch branch = branchRepository.findById(dto.getBranchId())
                .orElseThrow(() -> new RecordNotFoundException(BRANCH_NOT_FOUND));
        Balance balance = balanceRepository.findByBranchId(dto.getBranchId())
                .orElseThrow(() -> new RecordNotFoundException(BALANCE_NOT_FOUND));
        Student student = studentRepository.findById(dto.getTakerId())
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
        PaymentType paymentType = paymentTypeRepository.findById(dto.getPaymentTypeId())
                .orElseThrow(() -> new UserNotFoundException(PAYMENT_TYPE_NOT_FOUND));
        if (balance.getBalance() < dto.getSumma()) {
            throw new RecordNotFoundException(BALANCE_NOT_ENOUGH_SUMMA);
        }
        balance.setBalance(balance.getBalance() - dto.getSumma());
        ExpenseForStudent expenseForStudent = ExpenseForStudent.builder()
                .summa(dto.getSumma())
                .reason(dto.getReason())
                .createdTime(LocalDateTime.now())
                .taker(student)
                .branch(branch)
                .paymentType(paymentType)
                .build();
        expenseForStudentRepository.save(expenseForStudent);
        balanceRepository.save(balance);
        return new ApiResponse(SUCCESSFULLY, true);
    }

    @Override
    public ApiResponse getById(Integer integer) {
        return null;
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getAllByBranchId(Integer branchId, LocalDateTime startTime, LocalDateTime endTime) {
        List<AdditionalExpense> all = expenseForStudentRepository.findAllByBranchIdAndCreatedTimeBetweenOrderByCreatedTimeDesc(branchId, startTime, endTime);
        List<ExpenseResponse> expenseResponseList = new ArrayList<>();
        all.forEach(additionalExpense -> expenseResponseList.add(ExpenseResponse.from(additionalExpense)));
        return new ApiResponse(expenseResponseList, true);
    }

    @Override
    public ApiResponse update(ExpenseRequestDto dto) {
        ExpenseForStudent expenseForStudent = expenseForStudentRepository.findById(dto.getId()).orElseThrow(() -> new RecordNotFoundException(EXPENSE_NOT_FOUND));
        Balance balance = balanceRepository.findByBranchId(dto.getBranchId())
                .orElseThrow(() -> new RecordNotFoundException(BALANCE_NOT_FOUND));
        Student student = studentRepository.findById(dto.getTakerId())
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
        if (dto.getSumma() > expenseForStudent.getSumma()) {
            double v = dto.getSumma() - expenseForStudent.getSumma();
            if (balance.getBalance() >= v) {
                balance.setBalance(balance.getBalance() - v);
                balanceRepository.save(balance);
            } else {
                throw new RecordNotFoundException(BALANCE_NOT_ENOUGH_SUMMA);
            }
        } else if (dto.getSumma() < expenseForStudent.getSumma()) {
            double v = expenseForStudent.getSumma() - dto.getSumma();
            balance.setBalance(balance.getBalance() + v);
            balanceRepository.save(balance);
        }
        expenseForStudent.setTaker(student);
        expenseForStudent.setReason(dto.getReason());
        expenseForStudent.setSumma(dto.getSumma());
        expenseForStudentRepository.save(expenseForStudent);
        return new ApiResponse(SUCCESSFULLY, true);
    }

    @Override
    public ApiResponse delete(Integer integer) {
        return null;
    }
}
