package com.example.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TariffDto {

    private Integer id;
    private String name;

    private String description;

    private int branchAmount;

    private long productAmount;

    private int employeeAmount;

    private long tradeAmount;

    private String lifetime;

    private int testDay;

    private int interval;

    private double price;

    private double discount;

    private boolean active;

    private boolean delete;

    private List<Integer> permissionsList;
}
