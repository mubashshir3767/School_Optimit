package com.example.kitchen.repository;

import com.example.kitchen.entity.Meal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MealRepository extends JpaRepository<Meal, Integer> {

    List<Meal> findAllByBranchIdAndActiveTrue(Integer branchId);

    boolean existsByName(String name);
}
