package com.example.model.request;

import com.example.entity.Achievement;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AchievementDto {
    private Integer id;

    private String name;

    private String aboutAchievement;

    private MultipartFile photoCertificate;

    private Integer userId;

    public static List<AchievementDto> toAllResponse(List<Achievement> achievements) {
        List<AchievementDto> achievementDtoList = new ArrayList<>();
        achievements.forEach(achievement -> {
            achievementDtoList.add(toResponse(achievement));
        });
        return achievementDtoList;
    }

    public static AchievementDto toResponse(Achievement achievement) {
        return AchievementDto
                .builder()
                .id(achievement.getId())
                .name(achievement.getName())
                .aboutAchievement(achievement.getAboutAchievement())
                .build();
    }
}
