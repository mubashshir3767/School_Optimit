package com.example.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class DailyLessons {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private int lessonTime;

    private LocalDate day;

    private boolean active;

    @ManyToOne
    private TypeOfWork typeOfWork;

    @ManyToOne
    private User teacher;

    @ManyToOne
    private Subject subject;

    @ManyToOne
    private StudentClass studentClass;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Branch branch;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Room room;
}
