package com.example.kitchen.entity;

import com.example.entity.Branch;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String description;

    @ManyToOne
    private Measurement measurement;

    @JsonIgnore
    @ManyToOne
    private Branch branch;

    private boolean active;

    private double price;
}
