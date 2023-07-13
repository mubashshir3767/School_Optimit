package com.example.entity;

import com.example.model.request.SubjectRequest;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private int level;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Topic> topicList;


    public static Subject toSubject(SubjectRequest subjectRequest) {
        return Subject
                .builder()
                .name(subjectRequest.getName())
                .level(subjectRequest.getLevel())
                .build();
    }
}
