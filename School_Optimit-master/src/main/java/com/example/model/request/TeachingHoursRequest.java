package com.example.model.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeachingHoursRequest {

    private Integer id;

    private Integer typeOfWorkId;

    private int lessonHours;

    private String date;

    private Integer teacherId;

    private Integer classId;
    private List<Integer> classesIds;
}
