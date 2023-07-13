package com.example.controller;

import com.example.model.common.ApiResponse;
import com.example.model.request.ExpenseRequestDto;
import com.example.service.AdditionalExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/additional/expense")
public class AdditionalExpenseController {

    private final AdditionalExpenseService additionalExpenseService;

    @PostMapping("/create")
    public ApiResponse create(@RequestBody ExpenseRequestDto dto) {
        return additionalExpenseService.create(dto);
    }

    @PutMapping("/update")
    public ApiResponse update(@RequestBody ExpenseRequestDto dto) {
        return additionalExpenseService.update(dto);
    }

    @GetMapping("/getAllByBranchId")
    public ApiResponse getAllByBranchId(
            @RequestParam(name = "id") Integer id,
            @RequestParam(name = "startDate") LocalDateTime startDate,
            @RequestParam(name = "endDate") LocalDateTime endDate
    ) {
        return additionalExpenseService.getAllByBranchId(id, startDate, endDate);
    }
}
