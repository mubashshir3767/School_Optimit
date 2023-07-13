package com.example.model.response;

import com.example.entity.AdditionalExpense;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ExpenseResponse {


    private Integer id;

    private double summa;

    private String reason;

    private Integer takerId;

    private String takerName;

    private String paymentType;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createdTime;

    public static ExpenseResponse from(AdditionalExpense additionalExpense) {
        return ExpenseResponse.builder()
                .id(additionalExpense.getId())
                .summa(additionalExpense.getSumma())
                .reason(additionalExpense.getReason())
                .takerId(additionalExpense.getTaker().getId())
                .takerName(additionalExpense.getTaker().getFullName())
                .createdTime(additionalExpense.getCreatedTime())
                .paymentType(additionalExpense.getPaymentType().getName())
                .build();
    }
}
