package com.example.kitchen.controller;

import com.example.kitchen.model.MeasurementDto;
import com.example.kitchen.service.MeasurementService;
import com.example.model.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/measurement")
public class MeasurementController {

    private final MeasurementService measurementService;

    @PostMapping("/create")
    public ApiResponse create(@RequestBody MeasurementDto measurementDto) {
        return measurementService.create(measurementDto);
    }

    @GetMapping("/getById/{id}")
    public ApiResponse getById(@PathVariable Integer id) {
        return measurementService.getById(id);
    }

    @PutMapping("/update")
    public ApiResponse update(@RequestBody MeasurementDto measurementDto) {
        return measurementService.update(measurementDto);
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse delete(@PathVariable Integer id) {
        return measurementService.delete(id);
    }

    @GetMapping("/getByBranchId/{id}")
    public ApiResponse getByBranchId(@PathVariable Integer id) {
        return measurementService.getByBranchId(id);
    }

}
