package com.example.model.request;

import com.example.enums.SalaryType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TypeOfWorkRequest {

    private Integer id;//qachonki update bolsa shunda kerak boladi

    private String name;// asosiy | to'garaklar | vazifa darslar

    private double price;

}
