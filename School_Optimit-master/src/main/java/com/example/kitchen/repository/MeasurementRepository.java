package com.example.kitchen.repository;

import com.example.kitchen.entity.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeasurementRepository extends JpaRepository<Measurement,Integer> {

    List<Measurement> findAllByBranchIdAndActiveTrue(Integer branchId);

    boolean existsByNameAndBranchIdAndActiveTrue(String name, Integer branchId);
}
