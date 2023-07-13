package com.example.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TopicRequest {
    private Integer id;
    private String name;

    public TopicRequest(String topicName) {
        this.name=topicName;
    }
}
