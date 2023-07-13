package com.example.model.request;

import com.example.enums.Months;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OverallReportRequest {

    private Integer id;

    @Enumerated(EnumType.STRING)
    private Months month;

    private Integer userId;
}
