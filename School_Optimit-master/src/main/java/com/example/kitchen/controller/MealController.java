package com.example.kitchen.controller;

import com.example.kitchen.model.MealRequestDto;
import com.example.kitchen.service.MealService;
import com.example.model.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/meal")
public class MealController {

    private final MealService mealService;

    @PostMapping("/create")
    public ApiResponse create(@RequestBody MealRequestDto mealRequestDto) {
        return mealService.create(mealRequestDto);
    }

    @GetMapping("/getById/{id}")
    public ApiResponse getById(@PathVariable Integer id) {
        return mealService.getById(id);
    }

    @PutMapping("/update")
    public ApiResponse update(@RequestBody MealRequestDto mealRequestDto) {
        return mealService.update(mealRequestDto);
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse delete(@PathVariable Integer id) {
        return mealService.delete(id);
    }

}
