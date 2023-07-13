package com.example.entity;

import com.example.enums.Months;
import com.example.model.request.TeachingHoursRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class TeachingHours {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private int lessonHours;

    private LocalDate date;

    @ManyToOne
    private TypeOfWork typeOfWork;

    @ManyToOne
    @JsonIgnore
    private User teacher;

    @ElementCollection
    private List<Integer> classIds;

    public static TeachingHours toTeachingHours(TeachingHoursRequest teachingHoursRequest){
        return TeachingHours
                .builder()
                .lessonHours(teachingHoursRequest.getLessonHours())
                .build();
    }
}