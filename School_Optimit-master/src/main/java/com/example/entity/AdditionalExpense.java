package com.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class AdditionalExpense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private double summa;

    @Column(nullable = false)
    private String reason;

    @OneToOne
    private User taker;

    @OneToOne
    private PaymentType paymentType;

    @JsonIgnore
    @OneToOne
    private Branch branch;

    private LocalDateTime createdTime;
}
