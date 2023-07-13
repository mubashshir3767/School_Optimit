package com.example.kitchen.controller;

import com.example.kitchen.model.MealScheduleForDayDto;
import com.example.kitchen.model.MealScheduleForWeekDto;
import com.example.kitchen.service.MealScheduleForWeekService;
import com.example.model.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mealForWeek")
public class MealScheduleForWeekController {

    private final MealScheduleForWeekService mealScheduleForWeekService;

    @PostMapping("/create")
    public ApiResponse create(@RequestBody MealScheduleForWeekDto mealScheduleForWeekDto) {
        return mealScheduleForWeekService.create(mealScheduleForWeekDto);
    }

    @GetMapping("/getById/{id}")
    public ApiResponse getById(@PathVariable Integer id) {
        return mealScheduleForWeekService.getById(id);
    }

    @PutMapping("/update")
    public ApiResponse update(@RequestBody MealScheduleForWeekDto mealScheduleForWeekDto) {
        return mealScheduleForWeekService.update(mealScheduleForWeekDto);
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse delete(@PathVariable Integer id) {
        return mealScheduleForWeekService.delete(id);
    }

}
