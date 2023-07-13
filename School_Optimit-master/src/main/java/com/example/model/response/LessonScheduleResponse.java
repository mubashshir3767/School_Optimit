package com.example.model.response;

import com.example.entity.LessonSchedule;
import com.example.entity.Room;
import com.example.entity.StudentClass;
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
public class LessonScheduleResponse {


    private Integer id;

    private String subject;

    private String teacher;

    private StudentClass studentClass;

    private int durationLesson;

    private Room room;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime startTime;


    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime endTime;

    public static LessonScheduleResponse from(LessonSchedule schedule) {
        return LessonScheduleResponse.builder()
                .id(schedule.getId())
                .subject(schedule.getSubject().getName())
                .teacher(schedule.getTeacher().getFullName())
                .durationLesson(schedule.getDurationLesson())
                .startTime(schedule.getStartTime())
                .endTime(schedule.getEndTime())
                .studentClass(schedule.getStudentClass())
                .room(schedule.getRoom())
                .build();
    }
}
