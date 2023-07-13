package com.example.model.response;

import com.example.entity.Reason;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReasonResponse {

    private Integer id;

    private String reason;

    private Integer days;

    private String imageUrl;

    private Integer studentId;

    private String studentClass;

    private String studentName;

    private String studentSurname;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate startDate;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate endDate;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createDate;

    public static ReasonResponse from(Reason reason ,String imageUrl){
        return  ReasonResponse.builder()
                .id(reason.getId())
                .reason(reason.getReason())
                .days(reason.getDays())
                .imageUrl(imageUrl)
                .studentId(reason.getStudent().getId())
                .studentClass(reason.getStudent().getStudentClass().getClassName())
                .studentName(reason.getStudent().getFirstName())
                .studentSurname(reason.getStudent().getLastName())
                .startDate(reason.getStartDate())
                .endDate(reason.getEndDate())
                .createDate(reason.getCreateDate())
                .build();
    }


}
