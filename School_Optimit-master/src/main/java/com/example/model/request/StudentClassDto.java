package com.example.model.request;

import com.example.entity.StudentClass;
import com.example.entity.User;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentClassDto {

    private Integer id;

    @Column(nullable = false)
    private String className;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate startDate;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate endDate;

    private Integer roomId;

    private Integer branchId;

    private Integer classLeaderId;

    public static StudentClassDto toResponse(StudentClass studentClass) {
        if (studentClass==null)return null;

        return StudentClassDto
                .builder()
                .id(studentClass.getId())
                .className(studentClass.getClassName())
                .classLeaderId(studentClass.getClassLeader().getId())
                .roomId(studentClass.getRoom().getId())
                .branchId(studentClass.getBranch().getId())
                .build();
    }
}

