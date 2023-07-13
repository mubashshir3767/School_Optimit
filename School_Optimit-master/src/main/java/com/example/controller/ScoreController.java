package com.example.controller;

import com.example.model.common.ApiResponse;
import com.example.model.request.ScoreDto;
import com.example.model.request.ScoreRequestDto;
import com.example.service.ScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/score")
public class ScoreController {

    private final ScoreService scoreService;

    @PostMapping("/create")
    public ApiResponse create(@RequestBody ScoreRequestDto scoreRequestDto) {
        return scoreService.create(scoreRequestDto);
    }

//    @GetMapping("/getById/{id}")
//    public ApiResponse getById(@PathVariable Integer id) {
//        return scoreService.getById(id);
//    }

    @PutMapping("/update")
    public ApiResponse update(@RequestBody @Validated ScoreRequestDto scoreRequestDto) {
        return scoreService.update(scoreRequestDto);
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse delete(@PathVariable UUID id) {
        return scoreService.delete(id);
    }
    @PostMapping("/getAll")
    public ApiResponse getAllScores(@RequestBody ScoreDto scoreDto) {
        return scoreService.getAll(scoreDto);
    }

    @PostMapping("/getAllForStudentAndFamily")
    public ApiResponse getAllForStudentAndFamily(@RequestBody ScoreDto scoreDto) {
        return scoreService.getForStudentAndFamily(scoreDto);
    }

}
