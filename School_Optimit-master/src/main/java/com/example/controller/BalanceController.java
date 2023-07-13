package com.example.controller;

import com.example.entity.Balance;
import com.example.model.common.ApiResponse;
import com.example.service.BalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/balance")
public class BalanceController {

    private final BalanceService balanceService;

    @PostMapping("/create")
    public ApiResponse create(@RequestBody Balance balance) {
        return balanceService.create(balance);
    }

    @GetMapping("/getById/{id}")
    public ApiResponse getById(@PathVariable Integer id) {
        return balanceService.getById(id);
    }
}
