package com.example.service;

import com.example.entity.Role;
import com.example.enums.Constants;
import com.example.exception.RecordAlreadyExistException;
import com.example.exception.RecordNotFoundException;
import com.example.model.common.ApiResponse;
import com.example.model.request.RoleRequestDto;
import com.example.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService implements BaseService<RoleRequestDto, Integer> {

    private final RoleRepository roleRepository;
    private final PermissionService permissionService;

    @Override
    public ApiResponse create(RoleRequestDto requestDto) {
        checkIsSuccess(requestDto);
        Role role = Role.toRole(requestDto);
        role.setPermissions(permissionService.checkAllById(requestDto.getPermissionIdList()));
        return new ApiResponse(roleRepository.save(role), true);
    }

    @Override
    public ApiResponse getById(Integer id) {
        return new ApiResponse(checkById(id), true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse update(RoleRequestDto requestDto) {
        checkById(requestDto.getId());
        checkIsSuccess(requestDto);
        Role role = Role.toRole(requestDto);
        role.setId(requestDto.getId());
        role.setPermissions(permissionService.checkAllById(requestDto.getPermissionIdList()));
        return new ApiResponse(roleRepository.save(role), true);
    }

    @Override
    public ApiResponse delete(Integer id) {
        Role role = checkById(id);
        roleRepository.deleteById(id);
        return new ApiResponse(Constants.SUCCESSFULLY, true, role);
    }


    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getList(int size, int page) {
        Pageable pageable = PageRequest.of(size, page);
        Page<Role> roles = roleRepository.findAll(pageable);
        return new ApiResponse(roles, true);
    }

    private Role checkById(Integer id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(Constants.ROLE_NOT_AVAILABLE));
    }

    private void checkIsSuccess(RoleRequestDto requestDto) {
        if (requestDto.getName() == null)
            throw new RecordNotFoundException(Constants.NAME_NOT_FOUND);

        if (roleRepository.findByName(requestDto.getName()).isPresent())
            throw new RecordAlreadyExistException(Constants.ROLE_ALREADY_EXIST);
    }

    public List<Role> getAllByIds(List<Integer> roles) {
        return roleRepository.findAllById(roles);
    }
}
