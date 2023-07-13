package com.example.model.response;

import com.example.entity.Attachment;
import com.example.entity.Family;
import com.example.entity.Student;
import com.example.model.request.StudentDto;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentResponse {

    private Integer id;

    private String firstName;

    private String lastName;

    private String fatherName;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate birthDate;

    private String docNumber;

    private List<String> docPhoto;

    private String reference;

    private String photo;

    private String studentClass;

    private boolean active;

    private List<Family> families;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime addedTime;

    private String medDocPhoto;

    public static StudentResponse from(Student student){
        return StudentResponse.builder()
                .id(student.getId())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .fatherName(student.getFatherName())
                .birthDate(student.getBirthDate())
                .docNumber(student.getDocNumber())
                .families(student.getFamily())
                .studentClass(student.getStudentClass().getClassName())
                .active(student.isActive())
                .addedTime(student.getAddedTime())
                .build();
    }
}
