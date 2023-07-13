package com.example.kitchen.repository;

import com.example.kitchen.entity.Warehouse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarehouseRepository extends JpaRepository<Warehouse, Integer> {
 boolean existsByBranchIdAndActiveTrue(Integer branchId);

 Page<Warehouse> findAllByBranchIdAndActiveTrue(Integer branchId, Pageable page1);
}
