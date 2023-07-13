package com.example.service;

import com.example.entity.Achievement;
import com.example.entity.User;
import com.example.enums.Constants;
import com.example.exception.RecordAlreadyExistException;
import com.example.exception.RecordNotFoundException;
import com.example.exception.UserNotFoundException;
import com.example.model.common.ApiResponse;
import com.example.model.request.AchievementDto;
import com.example.repository.AchievementRepository;
import com.example.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AchievementService implements BaseService<AchievementDto, Integer> {

    private final AchievementRepository achievementRepository;
    private final AttachmentService attachmentService;
    private final UserRepository userRepository;


    @Override
    public ApiResponse create(AchievementDto achievementDto) {
        Achievement achievement = Achievement.toAchievement(achievementDto);
//        setPhoto(achievementDto, achievement);
        checkIfExist(achievementDto);
        setUser(achievement, achievementDto.getUserId());
        achievementRepository.save(achievement);
        return new ApiResponse(achievementDto, true);
    }

    private void setUser(Achievement achievement, Integer userID) {
        User user = userRepository.findById(userID).orElseThrow(() -> new RecordNotFoundException(Constants.USER_NOT_FOUND));
        achievement.setUser(user);
    }

    @Override
    public ApiResponse getById(Integer integer) {
        return new ApiResponse(checkById(integer), true);
    }


    @Override
    public ApiResponse update(AchievementDto achievementDto) {
        checkById(achievementDto.getId());
        Achievement achievement = Achievement.toAchievement(achievementDto);
        setUser(achievement, achievementDto.getUserId());
//        setPhoto(achievementDto,achievement);
        achievement.setId(achievementDto.getId());
        achievementRepository.save(achievement);
        return new ApiResponse(achievementDto, true);
    }

    @Override
    public ApiResponse delete(Integer integer) {
        Achievement achievement = checkById(integer);
        achievementRepository.deleteById(integer);
        return new ApiResponse(Constants.DELETED, true, achievement);
    }

    private Achievement checkById(Integer integer) {
        return achievementRepository.findById(integer)
                .orElseThrow(() -> new RecordNotFoundException(Constants.ACHIEVEMENT_NOT_FOUND));
    }

    private void checkIfExist(AchievementDto achievementDto) {
        if (achievementRepository.findByName(achievementDto.getName()).isPresent()) {
            throw new RecordAlreadyExistException(Constants.ACHIEVEMENT_ALREADY_EXIST);
        }
    }

    private void setPhoto(AchievementDto achievementDto, Achievement achievement) {
        if (!achievementDto.getPhotoCertificate().isEmpty()) {
            achievement.setPhotoCertificate(attachmentService.saveToSystem(achievementDto.getPhotoCertificate()));
        }
    }

}
