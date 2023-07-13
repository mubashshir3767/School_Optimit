package com.example.kitchen.model;

import com.example.kitchen.entity.Product;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponseList {

    private List<Product> products;

    private long totalElement;
    private int totalPage;
    private int size;
}
