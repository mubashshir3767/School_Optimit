package com.example.model.request;

import jakarta.persistence.Column;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ExpenseRequestDto {

    private Integer id;

    @Column(nullable = false)
    private double summa;

    @Column(nullable = false)
    private String reason;

    @Column(nullable = false)
    private Integer takerId;


    @Column(nullable = false)
    private Integer paymentTypeId;

    @Column(nullable = false)
    private Integer branchId;
}
