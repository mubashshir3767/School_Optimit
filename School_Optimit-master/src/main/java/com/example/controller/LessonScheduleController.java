package com.example.controller;

import com.example.model.common.ApiResponse;
import com.example.model.request.LessonScheduleDto;
import com.example.service.LessonScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/schedule")
public class LessonScheduleController {

    private final LessonScheduleService lessonScheduleService;

    @PostMapping("/create")
    public ApiResponse create(@RequestBody List<LessonScheduleDto> lessonScheduleDtoList) {
        return lessonScheduleService.create(lessonScheduleDtoList);
    }

    @GetMapping("/getById/{id}")
    public ApiResponse getById(@PathVariable Integer id) {
        return lessonScheduleService.getById(id);
    }

    @PutMapping("/update")
    public ApiResponse update(@RequestBody LessonScheduleDto lessonScheduleDto) {
        return lessonScheduleService.update(lessonScheduleDto);
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse delete(@PathVariable Integer id) {
        return lessonScheduleService.delete(id);
    }

    @GetMapping("/getAllActiveSchedule/{id}")
    public ApiResponse getAllActiveClasses(@PathVariable Integer id) {
        return lessonScheduleService.getAllByBranchId(id);
    }
}
