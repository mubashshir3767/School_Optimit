package com.example.entity;

import com.example.model.request.AchievementDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Achievement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String aboutAchievement;

    @OneToOne
    private Attachment photoCertificate;

    @ManyToOne
    private User user;

    public static Achievement toAchievement(AchievementDto achievement) {
        return Achievement
                .builder()
                .name(achievement.getName())
                .aboutAchievement(achievement.getAboutAchievement())
                .build();
    }
}
