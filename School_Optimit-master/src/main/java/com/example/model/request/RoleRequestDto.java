package com.example.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class RoleRequestDto {

    private Integer id;

    @NotBlank
    private String name;

    private String parentRole;

    @NotBlank
    private List<Integer> permissionIdList;
}
