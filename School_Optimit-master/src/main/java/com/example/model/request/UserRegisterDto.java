package com.example.model.request;

import com.example.entity.*;
import com.example.enums.Gender;
import com.example.enums.Position;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterDto {

    private Integer id;

    @NotBlank
    private String fullName;

    @NotBlank
    @Size(min = 9, max = 9)
    private String phoneNumber;

    @NotBlank
    @Size(min = 6)
    private String password;

    private int inn;

    private int inps;

    private String biography;

    private String birthDate;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private MultipartFile profilePhoto;

    private List<Integer> rolesIds;

    private List<Integer> subjectsIds;

    private List<Integer> dailyLessonsIds;

    private Position position;

    private String email;

    private boolean married;

    private Integer branchId;
}
