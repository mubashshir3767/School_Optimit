package com.example.entity;

import com.example.model.request.TopicRequest;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    public static Topic toTopic(TopicRequest topicRequest) {
        return Topic
                .builder()
                .name(topicRequest.getName())
                .build();
    }
}
