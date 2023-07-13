package com.example.controller;

import com.example.enums.Months;
import com.example.model.common.ApiResponse;
import com.example.model.request.OverallReportRequest;
import com.example.service.OverallReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/overallReport")
public class OverallReportController {

    private final OverallReportService overallReportService;

    @PostMapping("/save")
    public ApiResponse save(@RequestBody OverallReportRequest overallReportRequest){
        return overallReportService.create(overallReportRequest);
    }

    @GetMapping("/getById/{id}")
    public ApiResponse getById(@PathVariable Integer id){
        return overallReportService.getById(id);
    }

    @GetMapping("/getByIdAndMonth/{id}/{month}")
    public ApiResponse getByIdAndMonth(@PathVariable Integer id,
                                       @PathVariable Months month){
        return overallReportService.getByIdAndMonth(id,month);
    }

    @GetMapping("/getAll")
    public ApiResponse getAll(){
        return overallReportService.getAll();
    }


    @GetMapping("/update")
    public ApiResponse update(@RequestBody OverallReportRequest overallReportRequest){
        return overallReportService.update(overallReportRequest);
    }

    @GetMapping("/delete/{id}")
    public ApiResponse delete(@PathVariable Integer id){
        return overallReportService.delete(id);
    }
}
