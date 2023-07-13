package com.example.controller;

import com.example.model.common.ApiResponse;
import com.example.model.request.JournalRequestDto;
import com.example.model.request.ReasonRequestDto;
import com.example.service.ReasonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reason")
@RequiredArgsConstructor
public class ReasonController {

    private final ReasonService reasonService;

    @PostMapping("/create")
    public ApiResponse create(@ModelAttribute ReasonRequestDto reasonRequestDto) {
        return reasonService.create(reasonRequestDto);
    }
    @GetMapping("/getById/{id}")
    public ApiResponse getById(@PathVariable Integer id) {
        return reasonService.getById(id);
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse delete(@PathVariable Integer id) {
        return reasonService.delete(id);
    }

    @GetMapping("/getByBranchId/{id}")
    public ApiResponse getByBranchId(@PathVariable Integer id) {
        return reasonService.getByBranchId(id);
    }

    @GetMapping("/getByStudentId/{id}")
    public ApiResponse getByStudentId(@PathVariable Integer id) {
        return reasonService.getByStudentId(id);
    }

}
