package com.example.repository;

import com.example.entity.AdditionalExpense;
import com.example.entity.ExpenseForStudent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ExpenseForStudentRepository extends JpaRepository<ExpenseForStudent, Integer> {

    List<AdditionalExpense> findAllByBranchIdAndCreatedTimeBetweenOrderByCreatedTimeDesc(Integer branchId, LocalDateTime createdTime, LocalDateTime createdTime2);


}
