package com.example.entity;

import com.example.model.request.LessonScheduleDto;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class LessonSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Subject subject;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User teacher;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Branch branch;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private StudentClass studentClass;

    private int durationLesson;

    private boolean active;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Room room;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime startTime;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime endTime;

    public static LessonSchedule from(LessonScheduleDto scheduleDto){
        return LessonSchedule.builder()
                .durationLesson(scheduleDto.getDurationLesson())
                .startTime(scheduleDto.getStartTime())
                .endTime(scheduleDto.getEndTime())
                .active(true)
                .build();
    }
}
