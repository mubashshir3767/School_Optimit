package com.example.repository;

import com.example.entity.StudentClass;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface StudentClassRepository extends JpaRepository<StudentClass, Integer> {
//    List<StudentClass> findAllByBranchIdAndActiveTrue(Integer branchId);
    List<StudentClass> findAllByActiveTrueAndBranchId(Integer branchId);
    List<StudentClass> findAllByBranchIdAndStartDateAfterAndEndDateBeforeAndActiveFalse(Integer branchId, LocalDate startDate, LocalDate endDate);
}
