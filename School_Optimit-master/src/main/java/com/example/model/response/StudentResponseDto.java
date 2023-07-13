package com.example.model.response;

import com.example.entity.Student;
import com.example.entity.StudentClass;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentResponseDto {

    private Integer id;

    private String firstName;

    private String lastName;

    private StudentClass studentClass;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate birthDate;
    public static StudentResponseDto from(Student student){
        return StudentResponseDto.builder()
                .id(student.getId())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .birthDate(student.getBirthDate())
                .studentClass(student.getStudentClass())
                .build();
    }
}
