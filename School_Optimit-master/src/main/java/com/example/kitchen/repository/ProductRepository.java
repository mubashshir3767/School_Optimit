package com.example.kitchen.repository;

import com.example.kitchen.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product, Integer> {

    Page<Product> findAllByBranchIdAndActiveTrue(Integer branchId, Pageable pageable);

    boolean existsByBranchIdAndMeasurementIdAndNameAndActiveTrue(Integer branchId, Integer measurementId, String name);

}
