package com.example.kitchen.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class MeasurementDto {

    private Integer id;

    private String name;

    private Integer branchId;
}
