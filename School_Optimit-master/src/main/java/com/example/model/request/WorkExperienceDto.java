package com.example.model.request;

import com.example.entity.WorkExperience;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkExperienceDto {

    private Integer id;

    private String placeOfWork;

    private String position;

    private String startDate;

    private String endDate;

    private Integer employeeId;

    public static WorkExperienceDto toWorkExperienceDto(WorkExperience workExperience) {
        return WorkExperienceDto
                .builder()
                .id(workExperience.getId())
                .placeOfWork(workExperience.getPlaceOfWork())
                .startDate(workExperience.getStartDate().toString())
                .endDate(workExperience.getEndDate().toString())
                .position(workExperience.getPosition())
                .employeeId(workExperience.getEmployee().getId()).build();
    }

    public static List<WorkExperienceDto> toAllResponse(List<WorkExperience> workExperiences) {
        List<WorkExperienceDto> workExperienceDtoList = new ArrayList<>();
        workExperiences.forEach(workExperience -> {
            workExperienceDtoList.add(toWorkExperienceDto(workExperience));
        });
        return workExperienceDtoList;
    }
}
