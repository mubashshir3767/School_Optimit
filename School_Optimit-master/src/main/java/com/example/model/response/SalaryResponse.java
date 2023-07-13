package com.example.model.response;

import com.example.entity.Salary;
import com.example.enums.Months;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class SalaryResponse {

    private Integer id;

    @Enumerated
    private Months month;

    private double fix;

    private double currentMonthSalary;

    private double partlySalary;

    private double givenSalary;

    private double salary;

    private double cashAdvance;

    private double classLeaderSalary;

    private double amountDebt;

    public static SalaryResponse toResponse(Salary salary) {
        return SalaryResponse
                .builder()
                .id(salary.getId())
                .month(salary.getMonth())
                .amountDebt(salary.getAmountDebt())
                .classLeaderSalary(salary.getClassLeaderSalary())
                .salary(salary.getSalary())
                .partlySalary(salary.getPartlySalary())
                .givenSalary(salary.getGivenSalary())
                .fix(salary.getFix())
                .cashAdvance(salary.getCashAdvance())
                .currentMonthSalary(salary.getCurrentMonthSalary())
                .build();
    }

    public static List<SalaryResponse> toAllResponse(List<Salary> salaries) {
        List<SalaryResponse> salaryResponses = new ArrayList<>();
        salaries.forEach(salary -> {
            salaryResponses.add(SalaryResponse.toResponse(salary));
        });
        return salaryResponses;
    }
}
