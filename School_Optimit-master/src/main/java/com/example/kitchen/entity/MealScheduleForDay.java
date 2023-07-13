package com.example.kitchen.entity;

import com.example.entity.Branch;
import com.example.enums.WeekDays;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class MealScheduleForDay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private WeekDays weekDay;

    @ManyToMany
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Meal> mealList;

    @ManyToMany
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Drink> drinkList;


    @JsonIgnore
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Branch branch;

    private boolean active;
}
