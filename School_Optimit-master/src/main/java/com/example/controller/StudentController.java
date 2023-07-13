package com.example.controller;

import com.example.model.common.ApiResponse;
import com.example.model.request.FamilyLoginDto;
import com.example.model.request.StudentDto;
import com.example.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/student")
public class StudentController {

    private final StudentService service;

    @PostMapping("/create")
    public ApiResponse create(@ModelAttribute StudentDto studentDto) {
        return service.create(studentDto);
    }

    @GetMapping("/getById/{id}")
    public ApiResponse getById(@PathVariable Integer id) {
        return service.getById(id);
    }

    @PutMapping("/update")
    public ApiResponse update(@ModelAttribute StudentDto studentDto) {
        return service.update(studentDto);
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse delete(@PathVariable Integer id) {
        return service.delete(id);
    }

    @GetMapping("/getAll")
    public ApiResponse getAll(@RequestParam(name = "page", defaultValue = "0") int page,
                              @RequestParam(name = "size", defaultValue = "5") int size,
                              @RequestParam(name = "id") int id) {
        return service.getList(page, size,id);
    }

    @GetMapping("/getAllByClassId/{id}/{branchId}")
    public ApiResponse getAllByClassName(@PathVariable Integer id,@PathVariable Integer branchId) {
        return service.getListByClassNumber(id,branchId);
    }

    @GetMapping("/getAllNeActiveStudents/{branchId}")
    public ApiResponse getAllNeActiveStudents(@PathVariable Integer branchId) {
        return service.getAllNeActiveStudents(branchId);
    }

    @PostMapping("/studentLogin")
    public ApiResponse studentLogin(@RequestBody FamilyLoginDto studentLogin) {
        return service.studentLogIn(studentLogin);
    }

}
