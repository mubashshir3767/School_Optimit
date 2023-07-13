package com.example.kitchen.model;

import com.example.kitchen.entity.Drink;
import com.example.kitchen.entity.Meal;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DrinkResponse {


    private Integer id;

    private String name;


    public static DrinkResponse from(Drink drink) {
        return DrinkResponse.builder().id(drink.getId()).name(drink.getName()).build();
    }
}
