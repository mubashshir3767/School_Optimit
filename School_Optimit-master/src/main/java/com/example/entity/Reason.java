package com.example.entity;

import com.example.model.request.ReasonRequestDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Reason {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    private String reason;

    private Integer days;

    @OneToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Attachment image;

    @ManyToOne
    private Student student;

    private LocalDate startDate;

    private LocalDate endDate;

    private LocalDateTime createDate;

    private boolean active;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Branch branch;

    public static Reason from(ReasonRequestDto dto, Branch branch, Student student, Attachment attachment){
      return Reason.builder()
                .branch(branch)
                .student(student)
                .image(attachment)
                .reason(dto.getReason())
                .days(dto.getDays())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .createDate(LocalDateTime.now())
                .active(true)
                .build();
    }
}
