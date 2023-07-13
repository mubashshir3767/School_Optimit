package com.example.repository;

import com.example.entity.AdditionalExpense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AdditionalExpenseRepository extends JpaRepository<AdditionalExpense, Integer> {

    List<AdditionalExpense> findAllByBranchIdAndCreatedTimeBetweenOrderByCreatedTimeDesc(Integer branchId, LocalDateTime createdTime, LocalDateTime createdTime2);

//    List<Expense> findAllByBranchIdOrOrderByCreatedTimeBranchAsc(Integer branchId, Pageable pageable);

}
