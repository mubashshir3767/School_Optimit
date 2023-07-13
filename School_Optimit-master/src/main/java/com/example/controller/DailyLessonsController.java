package com.example.controller;

import com.example.model.request.DailyLessonsDto;
import com.example.model.common.ApiResponse;
import com.example.service.DailyLessonsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/dailyLessons")
public class DailyLessonsController {

    private final DailyLessonsService dailyLessonsService;

    @PostMapping("/create")
    public ApiResponse create(@RequestBody DailyLessonsDto dailyLessonDto) {
        return dailyLessonsService.create(dailyLessonDto);
    }

    @GetMapping("/getById/{id}")
    public ApiResponse getById(@PathVariable Integer id) {
        return dailyLessonsService.getById(id);
    }

    @GetMapping("/getAll")
    public ApiResponse getAll() {
        return dailyLessonsService.getAll();
    }

    @PutMapping("/update")
    public ApiResponse update(@RequestBody DailyLessonsDto dailyLessonDto) {
        return dailyLessonsService.update(dailyLessonDto);
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse delete(@PathVariable Integer id) {
        return dailyLessonsService.delete(id);
    }
}
