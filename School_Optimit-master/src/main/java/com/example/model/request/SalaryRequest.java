package com.example.model.request;

import com.example.enums.Months;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SalaryRequest {

    private Integer id;

    private String date;

    private Months month;

    private Integer userId;

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
}
