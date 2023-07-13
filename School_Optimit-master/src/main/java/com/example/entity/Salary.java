package com.example.entity;

import com.example.enums.Constants;
import com.example.enums.Months;
import com.example.exception.UserNotFoundException;
import com.example.model.request.SalaryHoursRequest;
import com.example.model.request.SalaryRequest;
import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Salary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDate date;

    private boolean active;

    @Enumerated(EnumType.STRING)
    private Months month;

    @ManyToOne
    private User user;

    private double fix;
    //fix bu kelishilgan oylik

    private double currentMonthSalary;
    // currentMonthSalary bu ishlagan oy uchun chiqgan maoshi
    // ya'ni 30 kundan 28 kun ishlagan bolishi mumkin

    private double partlySalary;
    // bu qisman oylik yani bu oy uchun 10mln olishi kk lekin 3mln qismi chiqarib berildi

    private double givenSalary;
    // bu berilgan summa

    private double salary;
    // bu qolgan  summa yani 10mlndan 7mln qolgan bolsa shunisi

    private double cashAdvance;
    // bu naqd shaklida pul kerak bolib qolganda olgan puli

    private double amountDebt;

    // bu oyni oxirida qarzdor bolgan summasi

    private double classLeaderSalary;

    public static Salary toSalary(SalaryRequest salaryRequest) {
        return Salary
                .builder()
                .fix(salaryRequest.getFix())
                .active(true)
                .month(salaryRequest.getMonth())
                .givenSalary(salaryRequest.getGivenSalary())
                .partlySalary(salaryRequest.getPartlySalary())
                .salary(salaryRequest.getSalary())
                .currentMonthSalary(salaryRequest.getCurrentMonthSalary())
                .cashAdvance(salaryRequest.getCashAdvance())
                .build();
    }

    public static Salary toSalaryCreate(SalaryRequest salaryRequest) {
        return Salary
                .builder()
                .fix(salaryRequest.getFix())
                .active(true)
                .month(salaryRequest.getMonth())
                .build();
    }

}