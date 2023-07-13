package com.example.controller;

import com.example.entity.StudentClass;
import com.example.model.common.ApiResponse;
import com.example.model.request.StudentClassDto;
import com.example.service.StudentClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/class")
public class StudentClassController {

    private final StudentClassService service;

    @PostMapping("/create")
    public ApiResponse create(@RequestBody StudentClassDto studentClass) {
        return service.create(studentClass);
    }

    @GetMapping("/getById/{id}")
    public ApiResponse getById(@PathVariable Integer id) {
        return service.getById(id);
    }

    @PutMapping("/update")
    public ApiResponse update(@RequestBody StudentClassDto studentClass) {
        return service.update(studentClass);
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse delete(@PathVariable Integer id) {
        return service.delete(id);
    }

    @GetMapping("/getAllActiveClasses/{id}")
    public ApiResponse getAllActiveClasses(@PathVariable Integer id) {
        return service.getAllActiveClasses(id);
    }

    @GetMapping("/getAllNeActiveClassesByYear/{id}")
    public ApiResponse getAllNeActiveClassesByYear(
            @RequestParam(name = "startDate") LocalDate startDate,
            @RequestParam(name = "endDate") LocalDate endDate,
            @PathVariable Integer id
            ) {
        return service.getAllNeActiveClassesByYear(startDate, endDate,id);
    }
}
