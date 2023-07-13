package com.example.model.response;

import com.example.model.request.LessonScheduleDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponseSchedule {

    private LessonScheduleDto lessonScheduleDto;

    private String massage;
}
