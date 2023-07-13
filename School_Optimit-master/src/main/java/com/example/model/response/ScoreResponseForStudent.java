package com.example.model.response;

import com.example.entity.Score;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScoreResponseForStudent {

    private UUID id;

    private char score;

    private String teacherName;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createdDate;

    public static ScoreResponseForStudent from(Score score) {
        return ScoreResponseForStudent.builder()
                .id(score.getId())
                .score(score.getScore())
                .teacherName(score.getTeacher().getFullName())
                .createdDate(score.getCreatedDate())
                .build();
    }

}
