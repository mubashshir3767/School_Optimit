package com.example.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Business {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    private String address;

    private String description;

    private String phoneNumber;

    private boolean active;

    private boolean delete;

    public static Business from(Business business){
        return Business.builder()
                .name(business.getName())
                .address(business.getAddress())
                .description(business.getDescription())
                .phoneNumber(business.getPhoneNumber())
                .active(true)
                .delete(false)
                .build();
    }
}
