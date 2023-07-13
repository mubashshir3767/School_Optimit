package com.example.entity;

import com.example.model.request.WorkExperienceDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class WorkExperience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String placeOfWork;

    private String position;

    private LocalDate startDate;

    private LocalDate endDate;

    @ManyToOne
    private User employee;

    public static WorkExperience toWorkExperience(WorkExperienceDto workExperienceDto) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = null;
        LocalDate endDate = null;
    try {
         startDate = LocalDate.parse(workExperienceDto.getStartDate(), formatter);
         endDate = LocalDate.parse(workExperienceDto.getEndDate(), formatter);
    }catch (Exception e){
        e.printStackTrace();
    }
        return WorkExperience
                .builder()
                .placeOfWork(workExperienceDto.getPlaceOfWork())
                .position(workExperienceDto.getPosition())
                .startDate(startDate)
                .endDate(endDate)
                .build();
    }
}
