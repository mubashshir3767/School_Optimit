package com.example.service;

import com.example.entity.Permission;
import com.example.enums.Constants;
import com.example.exception.RecordNotFoundException;
import com.example.model.common.ApiResponse;
import com.example.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PermissionService implements BaseService<Permission, Integer> {

    private final PermissionRepository permissionRepository;

    @Override
    public ApiResponse create(Permission permission) {
        if (permission.getName() == null) {
            throw new RecordNotFoundException(Constants.PERMISSION_NOT_FOUND);
        }
        return new ApiResponse(permissionRepository.save(permission), true);
    }

    @Override
    public ApiResponse getById(Integer id) {
        return new ApiResponse(checkById(id), true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getList() {
        return new ApiResponse(permissionRepository.findAll(), true);
    }

    public List<Permission> getAll() {
        return permissionRepository.findAll();
    }

    @Override
    public ApiResponse update(Permission newPermission) {
        checkById(newPermission.getId());
        return new ApiResponse(permissionRepository.save(newPermission), true);
    }

    @Override
    public ApiResponse delete(Integer id) {
        Permission permission = checkById(id);
        permissionRepository.deleteById(id);
        return new ApiResponse(Constants.SUCCESSFULLY, true,permission);
    }

    public Permission checkById(Integer id) {
        return permissionRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(Constants.PERMISSION_NOT_FOUND));
    }

    public boolean isEmpty() {
      return permissionRepository.findAll().isEmpty();
    }

    public List<Permission> checkAllById(List<Integer> permissionIdList) {
        return permissionRepository.findAllById(permissionIdList);
    }
}
