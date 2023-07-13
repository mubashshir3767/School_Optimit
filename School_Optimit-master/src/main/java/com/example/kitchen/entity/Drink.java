package com.example.kitchen.entity;

import com.example.entity.Branch;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Drink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToMany
    @JsonIgnore
    private List<Product> productList;

    @JsonIgnore
    @ManyToOne
    private Branch branch;

    private boolean active;
}
