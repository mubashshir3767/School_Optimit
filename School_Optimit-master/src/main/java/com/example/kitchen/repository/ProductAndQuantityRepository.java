package com.example.kitchen.repository;

import com.example.kitchen.entity.ProductAndQuantity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductAndQuantityRepository extends JpaRepository<ProductAndQuantity, Integer> {
    List<ProductAndQuantity> findAllByWarehouseIdOrderByQuantityDesc(Integer warehouseId);

    Optional<ProductAndQuantity> findByWarehouseIdAndProductId(Integer warehouseId, Integer productId);

    List<ProductAndQuantity> findAllByWarehouseId(Integer warehouseId);
}
