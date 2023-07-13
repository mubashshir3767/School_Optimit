package com.example.model.response;


import com.example.entity.TeachingHours;
import com.example.entity.TypeOfWork;
import com.example.entity.User;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class TeachingHoursResponse {

    private Integer id;

    private TypeOfWork typeOfWork;

    private int lessonHours;

    private String date;

    private Integer teacherId;
    private List<Integer> classesIds;

    public static TeachingHoursResponse teachingHoursDTO(TeachingHours teachingHours){
        return TeachingHoursResponse
                .builder()
                .id(teachingHours.getId())
                .typeOfWork(teachingHours.getTypeOfWork())
                .classesIds(teachingHours.getClassIds())
                .lessonHours(teachingHours.getLessonHours())
                .date(teachingHours.getDate().toString())
                .teacherId(teachingHours.getTeacher().getId())
                .build();
    }

    public static List<TeachingHoursResponse> toAllResponse(List<TeachingHours> teachingHoursList) {
        List<TeachingHoursResponse> teachingHoursResponses = new ArrayList<>();
        teachingHoursList.forEach(teachingHours1 -> {
            teachingHoursResponses.add(teachingHoursDTO(teachingHours1));
        });
        return teachingHoursResponses;
    }
}
