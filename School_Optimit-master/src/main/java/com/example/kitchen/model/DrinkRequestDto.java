package com.example.kitchen.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DrinkRequestDto {

    private Integer id;

    private String name;

    private List<Integer> productIdList;

    private Integer branchId;

    private boolean active;
}
