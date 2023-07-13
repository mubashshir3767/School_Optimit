package com.example.kitchen.controller;

import com.example.kitchen.model.DrinkRequestDto;
import com.example.kitchen.model.MealRequestDto;
import com.example.kitchen.service.DrinkService;
import com.example.model.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/drink")
public class DrinkController {

    private final DrinkService drinkService;

    @PostMapping("/create")
    public ApiResponse create(@RequestBody DrinkRequestDto drinkRequestDto) {
        return drinkService.create(drinkRequestDto);
    }

    @GetMapping("/getById/{id}")
    public ApiResponse getById(@PathVariable Integer id) {
        return drinkService.getById(id);
    }

    @PutMapping("/update")
    public ApiResponse update(@RequestBody DrinkRequestDto drinkRequestDto) {
        return drinkService.update(drinkRequestDto);
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse delete(@PathVariable Integer id) {
        return drinkService.delete(id);
    }

}
