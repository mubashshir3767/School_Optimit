package com.example.model.request;

import com.example.entity.Topic;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubjectRequest {

    private Integer id;
    private String name;
    private int level;
    private List<TopicRequest> topicList;
}
