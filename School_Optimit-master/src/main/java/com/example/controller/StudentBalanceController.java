package com.example.controller;

import com.example.model.common.ApiResponse;
import com.example.model.request.StudentAccountDto;
import com.example.service.StudentBalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
public class StudentBalanceController {

    private final StudentBalanceService studentBalanceService;

    @PostMapping("/create")
    public ApiResponse create(@RequestBody StudentAccountDto studentAccount) {
        return studentBalanceService.create(studentAccount);
    }
    @GetMapping("/getById/{id}")
    public ApiResponse getById(@PathVariable Integer id) {
        return studentBalanceService.getById(id);
    }

    @PutMapping("/update")
    public ApiResponse update(@RequestBody StudentAccountDto studentAccount) {
        return studentBalanceService.update(studentAccount);
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse delete(@PathVariable Integer id) {
        return studentBalanceService.delete(id);
    }

    @GetMapping("/getByBranchId/{id}")
    public ApiResponse getByBranchId(@PathVariable Integer id) {
        return studentBalanceService.getByBranchId(id);
    }

    @GetMapping("/getByStudentId/{id}")
    public ApiResponse getByStudentId(@PathVariable Integer id) {
        return studentBalanceService.getByStudentId(id);
    }
}
