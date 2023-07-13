package com.example.repository;

import com.example.entity.StudentBalance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentBalanceRepository extends JpaRepository<StudentBalance, Integer> {


    Optional<StudentBalance> findByStudentIdAndActiveTrue(Integer integer);
    Optional<StudentBalance> findByStudentId(Integer integer);

    List<StudentBalance> findAllByBranchIdAndActiveTrueOrderByCreatedDateAsc(Integer integer);
}
