package com.example.controller;

import com.example.model.common.ApiResponse;
import com.example.model.request.AchievementDto;
import com.example.service.AchievementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/achievement")
public class AchievementController {

    private final AchievementService achievementService;

    @PostMapping("save")
    public ApiResponse save(@RequestBody AchievementDto achievementDto) {
        return achievementService.create(achievementDto);
    }

    @GetMapping("getById/{id}")
    public ApiResponse getById(@PathVariable Integer id) {
        return achievementService.getById(id);
    }


    @PutMapping("update")
    public ApiResponse update(@RequestBody AchievementDto achievementDto) {
        return achievementService.update(achievementDto);
    }

    @DeleteMapping("delete/{id}")
    public ApiResponse delete(@PathVariable Integer id) {
        return achievementService.delete(id);
    }

}
