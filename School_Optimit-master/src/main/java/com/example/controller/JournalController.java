package com.example.controller;

import com.example.model.common.ApiResponse;
import com.example.model.request.JournalRequestDto;
import com.example.model.request.ScoreDto;
import com.example.model.request.ScoreRequestDto;
import com.example.service.JournalService;
import com.example.service.ScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/journal")
public class JournalController {

    private final JournalService journalService;

    @PostMapping("/create")
    public ApiResponse create(@RequestBody JournalRequestDto journalRequestDto) {
        return journalService.create(journalRequestDto);
    }

    @GetMapping("/getById/{id}")
    public ApiResponse getById(@PathVariable Integer id) {
        return journalService.getById(id);
    }
    @GetMapping("/getByBranchId/{id}")
    public ApiResponse getByBranchId(@PathVariable Integer id) {
        return journalService.getAllByIdBranchId(id);
    }

    @PutMapping("/update")
    public ApiResponse update(@RequestBody JournalRequestDto journalRequestDto) {
        return journalService.update(journalRequestDto);
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse delete(@PathVariable Integer id) {
        return journalService.delete(id);
    }

}
