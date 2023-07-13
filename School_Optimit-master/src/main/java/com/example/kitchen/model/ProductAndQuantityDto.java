package com.example.kitchen.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductAndQuantityDto {

    private Integer productId;

    private double quantity;

    private double totalPrice;
}
