package com.example.kitchen.model;

import com.example.enums.WeekDays;
import com.example.kitchen.entity.MealScheduleForDay;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class MealScheduleForDayResponse {

    private Integer id;

    private WeekDays weekDay;

    private List<MealResponse> mealList;

    public static MealScheduleForDayResponse from(MealScheduleForDay mealScheduleForDay, List<MealResponse> mealResponse) {
        return MealScheduleForDayResponse.builder()
                .id(mealScheduleForDay.getId())
                .weekDay(mealScheduleForDay.getWeekDay())
                .mealList(mealResponse)
                .build();
    }
}
