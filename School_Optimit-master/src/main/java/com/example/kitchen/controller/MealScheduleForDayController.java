package com.example.kitchen.controller;

import com.example.kitchen.model.MealScheduleForDayDto;
import com.example.kitchen.service.MealScheduleForDayService;
import com.example.model.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mealForDay")
public class MealScheduleForDayController {

    private final MealScheduleForDayService mealScheduleForDayService;

    @PostMapping("/create")
    public ApiResponse create(@RequestBody MealScheduleForDayDto mealScheduleForDayDto) {
        return mealScheduleForDayService.create(mealScheduleForDayDto);
    }

    @GetMapping("/getById/{id}")
    public ApiResponse getById(@PathVariable Integer id) {
        return mealScheduleForDayService.getById(id);
    }

    @PutMapping("/update")
    public ApiResponse update(@RequestBody MealScheduleForDayDto mealScheduleForDayDto) {
        return mealScheduleForDayService.update(mealScheduleForDayDto);
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse delete(@PathVariable Integer id) {
        return mealScheduleForDayService.delete(id);
    }

}
