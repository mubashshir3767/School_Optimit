package com.example.kitchen.model;

import com.example.kitchen.entity.Measurement;
import com.example.kitchen.entity.Product;
import com.example.kitchen.entity.Warehouse;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MeasurementResponseList {

    private List<Warehouse> measurements;

    private long totalElement;
    private int totalPage;
    private int size;
}
