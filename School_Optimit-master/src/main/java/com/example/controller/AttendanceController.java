//package com.example.controller;
//
//import com.example.model.common.ApiResponse;
//import com.example.model.request.AttendanceRequestDto;
//import com.example.model.request.ScoreDto;
//import com.example.model.request.ScoreRequestDto;
//import com.example.service.AttendanceService;
//import com.example.service.ScoreService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.UUID;
//
//@RequiredArgsConstructor
//@RestController
//@RequestMapping("/api/v1/attendance")
//public class AttendanceController {
//
//    private final AttendanceService attendanceService;
//
//    @PostMapping("/create")
//    public ApiResponse create(@RequestBody AttendanceRequestDto attendanceRequestDto) {
//        return attendanceService.create(attendanceRequestDto);
//    }
//
////    @GetMapping("/getById/{id}")
////    public ApiResponse getById(@PathVariable Integer id) {
////        return attendanceService.getById(id);
////    }
//
//    @PutMapping("/update")
//    public ApiResponse update(@RequestBody @Validated AttendanceRequestDto attendanceRequestDto) {
//        return attendanceService.update(attendanceRequestDto);
//    }
//
////    @DeleteMapping("/delete/{id}")
////    public ApiResponse delete(@PathVariable UUID id) {
////        return attendanceService.delete(id);
////    }
//    @GetMapping("/getAll/{id}")
//    public ApiResponse getAllScores(@PathVariable Integer id) {
//        return attendanceService.getAll(id);
//    }
//
//    @PostMapping("/getForFamily")
//    public ApiResponse getForFamily(@RequestBody ScoreDto scoreDto) {
//        return attendanceService.getForStudentAndFamily(scoreDto);
//    }
//}
