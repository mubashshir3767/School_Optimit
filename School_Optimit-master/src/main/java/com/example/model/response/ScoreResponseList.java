package com.example.model.response;


import com.example.entity.Score;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScoreResponseList {

    private List<ScoreResponseForStudent> scoreList;
    private long allSize;
    private int allPage;
    private int currentPage;
}
