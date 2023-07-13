package com.example.kitchen.repository;

import com.example.kitchen.entity.Drink;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DrinkRepository extends JpaRepository<Drink, Integer> {

    List<Drink> findAllByBranchIdAndActiveTrue(Integer branchId);

    boolean existsByName(String name);
}
