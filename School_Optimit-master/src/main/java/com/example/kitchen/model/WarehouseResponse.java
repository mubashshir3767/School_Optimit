package com.example.kitchen.model;

import com.example.kitchen.entity.ProductAndQuantity;
import com.example.kitchen.entity.Warehouse;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WarehouseResponse {

    private Warehouse warehouse;

    private List<ProductAndQuantity> productAndQuantities;
}
