package com.example.controller;

import com.example.model.common.ApiResponse;
import com.example.model.request.RoleRequestDto;
import com.example.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/role")
public class RoleController {

    private final RoleService roleService;

    @PostMapping("/save")
//    @PreAuthorize("hasAuthority('ROLE_ACCESS' or 'SUPER_ADMIN')")
    public ApiResponse save(@RequestBody RoleRequestDto requestDto) {
        return roleService.create(requestDto);
    }

    @PutMapping("/update")
//    @PreAuthorize("hasAuthority('ROLE_ACCESS' or 'SUPER_ADMIN')")
    public ApiResponse update(@RequestBody RoleRequestDto requestDto) {
        return roleService.update(requestDto);
    }

    @GetMapping("/getRoleByID/{id}")
    public ApiResponse getRoleByID(@PathVariable Integer id) {
        return roleService.getById(id);
    }

    @GetMapping("/getList")
    public ApiResponse getList(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "page", defaultValue = "5") int size
    ) {
        return roleService.getList(size,page);
    }

    @DeleteMapping("/delete/{id}")
//    @PreAuthorize("hasAuthority('ROLE_ACCESS' or 'SUPER_ADMIN')")
    public ApiResponse remove(@PathVariable Integer id) {
        return roleService.delete(id);
    }
}
