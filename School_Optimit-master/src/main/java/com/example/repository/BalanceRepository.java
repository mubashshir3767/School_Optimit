package com.example.repository;

import com.example.entity.Balance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BalanceRepository extends JpaRepository<Balance,Integer> {

    Optional<Balance> findByBranchId(Integer branchId);

    boolean existsByBranchId(Integer branchId);
}
