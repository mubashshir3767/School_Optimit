package com.example.repository;

import com.example.entity.Salary;
import com.example.enums.Months;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SalaryRepository extends JpaRepository<Salary,Integer> {

    List<Salary> findAllByUserId(Integer userId);
    Optional<Salary> findByIdAndMonthAndActiveTrue(Integer id, Months months);
}
