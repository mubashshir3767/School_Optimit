package com.example.kitchen.repository;

import com.example.kitchen.entity.MealScheduleForDay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MealScheduleForDayRepository extends JpaRepository<MealScheduleForDay, Integer> {
    List<MealScheduleForDay> findAllByBranchIdAndActiveTrue(Integer integer);
}
