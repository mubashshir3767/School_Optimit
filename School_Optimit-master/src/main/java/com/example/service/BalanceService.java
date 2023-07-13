package com.example.service;

import com.example.entity.Balance;
import com.example.entity.Branch;
import com.example.exception.RecordAlreadyExistException;
import com.example.exception.RecordNotFoundException;
import com.example.model.common.ApiResponse;
import com.example.repository.BalanceRepository;
import com.example.repository.BranchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import static com.example.enums.Constants.*;

@Service
@RequiredArgsConstructor
public class BalanceService implements BaseService<Balance, Integer> {

    private final BalanceRepository balanceRepository;
    private final BranchRepository branchRepository;

    @Override
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse create(Balance dto) {
        if (balanceRepository.existsByBranchId(dto.getCurrentBranch())) {
            throw new RecordAlreadyExistException(BALANCE_ALREADY_EXIST);
        }
        Branch branch = branchRepository.findById(dto.getCurrentBranch())
                .orElseThrow(() -> new RecordNotFoundException(BRANCH_NOT_FOUND));
        Balance balance = Balance.builder()
                .balance(dto.getBalance())
                .branch(branch)
                .shotNumber(dto.getShotNumber())
                .build();
        balanceRepository.save(balance);
        return new ApiResponse(SUCCESSFULLY, true);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getById(Integer integer) {
        Balance balance = balanceRepository.findById(integer).orElseThrow(() -> new RecordNotFoundException(BRANCH_NOT_FOUND));
        return new ApiResponse(balance, true);
    }

    @Override
    public ApiResponse update(Balance dto) {
        return null;
    }

    @Override
    public ApiResponse delete(Integer integer) {
        return null;
    }
}
