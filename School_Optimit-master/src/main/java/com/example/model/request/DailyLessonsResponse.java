package com.example.model.request;

import com.example.entity.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DailyLessonsResponse {

    private Integer id;

    private int lessonTime;

    private String day;

    private TypeOfWork typeOfWork;

    private Integer teacherId;

    private Subject subject;

    private StudentClassDto studentClass;

    private Branch branch;

    private Room room;

    public static DailyLessonsResponse toResponse(DailyLessons dailyLessons) {
        return DailyLessonsResponse
                .builder()
                .id(dailyLessons.getId())
                .lessonTime(dailyLessons.getLessonTime())
                .day(dailyLessons.getDay().toString())
                .subject(dailyLessons.getSubject())
                .typeOfWork(dailyLessons.getTypeOfWork())
                .branch(dailyLessons.getBranch())
                .teacherId(dailyLessons.getTeacher().getId())
                .studentClass(StudentClassDto.toResponse(dailyLessons.getStudentClass()))
                .room(dailyLessons.getRoom())
                .build();
    }

    public static List<DailyLessonsResponse> toAllResponse(List<DailyLessons> all) {
        List<DailyLessonsResponse> dailyLessonsResponses = new ArrayList<>();
        all.forEach(dailyLessons -> {
            dailyLessonsResponses.add(toResponse(dailyLessons));
        });
        return dailyLessonsResponses;
    }
}
