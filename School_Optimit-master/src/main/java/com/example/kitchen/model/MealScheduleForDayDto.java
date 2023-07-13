package com.example.kitchen.model;

import com.example.enums.WeekDays;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MealScheduleForDayDto {

    private Integer id;

    private WeekDays weekDay;

    private List<Integer> mealIdList;

    private List<Integer> drinkIdList;

    private Integer branchId;

}
