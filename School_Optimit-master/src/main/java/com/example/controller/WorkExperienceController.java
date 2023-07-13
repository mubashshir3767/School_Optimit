package com.example.controller;

import com.example.model.common.ApiResponse;
import com.example.model.request.WorkExperienceDto;
import com.example.service.WorkExperienceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/workExperience/")
public class WorkExperienceController {

    private final WorkExperienceService workExperienceService;

    @PostMapping("save")
    public ApiResponse save(@RequestBody WorkExperienceDto workExperience) {
        return workExperienceService.create(workExperience);
    }

    @GetMapping("getById/{id}")
    public ApiResponse getById(@PathVariable Integer id) {
        return workExperienceService.getById(id);
    }


    @PutMapping("update")
    public ApiResponse update(@RequestBody WorkExperienceDto workExperience) {
        return workExperienceService.update(workExperience);
    }

    @DeleteMapping("delete/{id}")
    public ApiResponse delete(@PathVariable Integer id) {
        return workExperienceService.delete(id);
    }
}
