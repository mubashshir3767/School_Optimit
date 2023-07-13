package com.example.model.response;

import com.example.entity.Journal;
import com.example.entity.Score;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class JournalResponseDto {

    private Journal journal;

    private List<Score> scoreList;
    private List<Score> attendanceList;
}
