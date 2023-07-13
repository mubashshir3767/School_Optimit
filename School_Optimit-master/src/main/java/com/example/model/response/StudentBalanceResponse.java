package com.example.model.response;

import com.example.entity.StudentBalance;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentBalanceResponse {

    private Integer id;

    private Double balance;

    private StudentResponseDto student;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createdDate;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime updatedDate;

    public static StudentBalanceResponse from(StudentBalance account){
        return StudentBalanceResponse.builder()
                .id(account.getId())
                .balance(account.getBalance())
                .student(StudentResponseDto.from(account.getStudent()))
                .createdDate(account.getCreatedDate())
                .updatedDate(account.getUpdatedDate())
                .build();
    }

}
