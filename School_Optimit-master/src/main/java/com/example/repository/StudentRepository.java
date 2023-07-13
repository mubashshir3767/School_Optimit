package com.example.repository;

import com.example.entity.Family;
import com.example.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Integer> {

    List<Student> findAllByStudentClassIdAndBranchIdAndActiveTrue(Integer studentClassId, Integer branchId);

    Page<Student> findAllByBranchIdAndActiveTrue(Pageable pageable, Integer id);

    List<Student> findAllByBranchIdAndActiveFalseOrderByAddedTimeAsc(Integer branchId);

    boolean existsByUsername(String username);

    List<Student> findByFamilyIn(List<Family> family);

    Optional<Student> findByUsernameAndPassword(String username, String password);
}
