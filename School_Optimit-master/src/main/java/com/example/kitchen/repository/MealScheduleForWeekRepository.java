package com.example.kitchen.repository;

import com.example.kitchen.entity.MealScheduleForWeek;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MealScheduleForWeekRepository extends JpaRepository<MealScheduleForWeek, Integer> {

    List<MealScheduleForWeek> findAllByBranchIdAndActiveTrue(Integer integer);
}
