package com.example.repository;

import com.example.entity.Journal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface JournalRepository extends JpaRepository<Journal, Integer> {

    List<Journal> findAllByBranchIdAndActiveTrue(Integer branchId);

    Optional<Journal> findByStudentClassIdAndActiveTrue(Integer studentClass_id);

    boolean existsByStudentClassIdAndActiveTrue(Integer studentClassId);
}
