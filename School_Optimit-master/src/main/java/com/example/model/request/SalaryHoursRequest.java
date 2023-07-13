package com.example.model.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SalaryHoursRequest {

    private Integer id;

    private String date;

    private Integer userId;

    private double currentMonthSalary;

    private double partlySalary;

    private double givenSalary;

    private double remainingSalary;

    private double cashAdvance;

    private double amountDebt;

    private double classLeaderSalary;
}
