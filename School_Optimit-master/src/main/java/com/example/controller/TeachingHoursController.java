package com.example.controller;

import com.example.model.common.ApiResponse;
import com.example.model.request.TeachingHoursRequest;
import com.example.service.TeachingHoursService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/teachingHours/")
public class TeachingHoursController {

    private final TeachingHoursService teachingHoursService;

    @PostMapping("/save")
//    @PreAuthorize("hasAnyRole('SUPER_ADMIN') or hasAnyAuthority('SAVE_TARIFF')")
    public ApiResponse save(@RequestBody TeachingHoursRequest teachingHoursRequest) {
        return teachingHoursService.create(teachingHoursRequest);
    }

    @GetMapping("/getById/{id}")
    public ApiResponse getById(@PathVariable Integer id) {
        return teachingHoursService.getById(id);
    }

    @GetMapping("/getByTeacherIdAndDate/{id}/{date}")
    public ApiResponse getByTeacherIdAndDate(@PathVariable Integer id,
                               @PathVariable String date
    ) {
        return teachingHoursService.getByTeacherIdAndDate(id, date);
    }

    @GetMapping("/getByTeacherId/{id}")
    public ApiResponse getByTeacherId(@PathVariable Integer id) {
        return teachingHoursService.getByTeacherId(id);
    }

    @GetMapping("/getAll")
    public ApiResponse getAll() {
        return teachingHoursService.getAll();
    }

    @PostMapping("/incrementHours")
    public ApiResponse incrementHours(@RequestBody TeachingHoursRequest teachingHoursRequest) {
        return teachingHoursService.incrementHours(teachingHoursRequest);
    }

    @PostMapping("/decrementHours")
    public ApiResponse decrementHours(@RequestBody TeachingHoursRequest teachingHoursRequest) {
        return teachingHoursService.decrementHours(teachingHoursRequest);
    }

    @PutMapping("/update")
//    @PreAuthorize("hasAnyRole('SUPER_ADMIN') or hasAnyAuthority('UPDATE_TARIFF')")
    public ApiResponse update(@RequestBody TeachingHoursRequest teachingHoursRequest) {
        return teachingHoursService.update(teachingHoursRequest);
    }

    @DeleteMapping("/remove/{id}")
//    @PreAuthorize("hasAnyRole('SUPER_ADMIN') or hasAnyAuthority('REMOVE_TARIFF')")
    public ApiResponse remove(@PathVariable Integer id) {
        return teachingHoursService.delete(id);
    }
}
