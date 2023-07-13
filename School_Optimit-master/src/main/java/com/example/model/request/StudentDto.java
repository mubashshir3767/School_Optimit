package com.example.model.request;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class StudentDto {

    private Integer id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String fatherName;

    private LocalDate birthDate;

    @Column(nullable = false)
    private String docNumber;

    @Column(nullable = false)
    private List<MultipartFile> docPhoto;

    private MultipartFile reference;

    @Column(nullable = false)
    private MultipartFile photo;

    private Integer studentClassId;

    private Integer branchId;

    private boolean active;

    private MultipartFile medDocPhoto;

    private String username;  // phoneNumber

    private String password;

}
