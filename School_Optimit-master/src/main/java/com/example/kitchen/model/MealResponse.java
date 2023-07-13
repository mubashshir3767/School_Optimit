package com.example.kitchen.model;

import com.example.kitchen.entity.Meal;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MealResponse {


    private Integer id;

    private String name;


    public static MealResponse from(Meal meal) {
        return MealResponse.builder().id(meal.getId()).name(meal.getName()).build();
    }
}
