package com.example.controller;

import com.example.model.common.ApiResponse;
import com.example.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/permission/")
@RequiredArgsConstructor
public class PermissionController {

    private final PermissionService permissionService;

    @GetMapping("getPermissionList")
    public ApiResponse getList() {
        return permissionService.getList();
    }

    @GetMapping("getById/{id}")
    public ApiResponse getList(@PathVariable Integer id) {
        return permissionService.getById(id);
    }
}
