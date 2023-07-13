package com.example.model.request;

import lombok.*;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JournalRequestDto {

    private Integer id;

    private Integer studentClassId;

    private Integer branchId;

    List<Integer> subjectIdList;

}
