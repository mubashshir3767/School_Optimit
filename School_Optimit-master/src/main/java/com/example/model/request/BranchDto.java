package com.example.model.request;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BranchDto {

    private Integer id;

    @Column(nullable = false)
    private String name;

    private Integer businessId;

}
