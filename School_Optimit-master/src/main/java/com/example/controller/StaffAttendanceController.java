package com.example.controller;

import com.example.model.common.ApiResponse;
import com.example.model.request.StaffAttendanceRequest;
import com.example.service.StaffAttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/staffAttendance/")
public class StaffAttendanceController {

    private final StaffAttendanceService service;
    @PostMapping("save")
    public ApiResponse save(@RequestBody StaffAttendanceRequest staffAttendanceRequest){
        return service.create(staffAttendanceRequest);
    }

    @GetMapping("getById/{id}")
    public ApiResponse getById(@PathVariable Integer id){
        return service.getById(id);
    }

    @GetMapping("getAll")
    public ApiResponse getAll(){
        return service.getAll();
    }

    @PutMapping("update")
    public ApiResponse update(@RequestBody StaffAttendanceRequest staffAttendanceRequest){
        return service.update(staffAttendanceRequest);
    }

    @GetMapping("delete/{id}")
    public ApiResponse delete(@PathVariable Integer id){
        return service.delete(id);
    }
}
