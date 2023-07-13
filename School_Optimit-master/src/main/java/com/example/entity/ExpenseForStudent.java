package com.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import static com.example.enums.Constants.RETURN_MONEY_FOR_MEAL;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class ExpenseForStudent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private double summa;

    @Column(nullable = false)
    private String reason;

    @OneToOne
    private Student taker;

    @OneToOne
    private PaymentType paymentType;

    @JsonIgnore
    @OneToOne
    private Branch branch;

    private LocalDateTime createdTime;

    public static ExpenseForStudent from(double summa,String reason,Student student, Branch branch, PaymentType paymentType){
        return ExpenseForStudent.builder()
                .summa(summa)
                .reason(RETURN_MONEY_FOR_MEAL)
                .createdTime(LocalDateTime.now())
                .taker(student)
                .branch(branch)
                .paymentType(paymentType)
                .build();
    }
}
