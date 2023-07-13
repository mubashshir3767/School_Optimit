package com.example.model.response;

import com.example.entity.*;
import com.example.enums.Months;
import com.example.enums.Position;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class OverallReportResponse {

    private Integer id;

    @Enumerated(EnumType.STRING)
    private Position position;

    @Enumerated(EnumType.STRING)
    private Months month;

    private String classLeadership;

    private SalaryResponse salary;

    private String fullName;

    private List<TeachingHoursResponse> teachingHours;

    private Branch branch;


    public static OverallReportResponse toOverallResponse(OverallReport overallReport) {
        return OverallReportResponse
                .builder()
                .id(overallReport.getId())
                .classLeadership(overallReport.getClassLeadership())
                .month(overallReport.getMonth())
                .branch(overallReport.getBranch())
                .teachingHours(TeachingHoursResponse.toAllResponse(overallReport.getUser().getTeachingHours()))
                .position(overallReport.getPosition())
                .salary(SalaryResponse.toResponse(overallReport.getSalary()))
                .fullName(overallReport.getUser().getFullName())
                .build();
    }

    public static List<OverallReportResponse> toAllOverallResponse(List<OverallReport> all) {
        List<OverallReportResponse> overallReportResponses = new ArrayList<>();
        all.forEach(overallReport -> {
            overallReportResponses.add(toOverallResponse(overallReport));
        });
        return overallReportResponses;
    }
}
