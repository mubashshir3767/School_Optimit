package com.example.model.response;

import com.example.entity.Family;
import com.example.enums.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FamilyResponse {

    private Integer id;

    private String fullName;

    private String phoneNumber;

    private String password;

    private String fireBaseToken;

    private Gender gender;

    private boolean active;
    public static FamilyResponse from(Family family){
        return FamilyResponse.builder()
                .id(family.getId())
                .fullName(family.getFullName())
                .phoneNumber(family.getPhoneNumber())
                .fireBaseToken(family.getFireBaseToken()==null ? null : family.getFireBaseToken() )
                .gender(family.getGender())
                .active(family.isActive())
                .build();
    }
}
