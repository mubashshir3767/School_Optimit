package com.example.model.response;

import com.example.entity.*;
import com.example.model.request.AchievementDto;
import com.example.model.request.StudentClassDto;
import com.example.model.request.WorkExperienceDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {

    private Integer id;

    private int inn;

    private int inps;

    private String fullName;

    private String biography;

    private String registeredDate;

    private String phoneNumber;

    private String profilePhotoUrl;

    private String birthDate;

    private String gender;

    private Attachment profilePhoto;

    private StudentClassDto studentClass;

    private List<AchievementDto> achievements;

    private List<WorkExperienceDto> workExperiences;

    private List<DailyLessons> dailyLessons;

    private List<Subject> subjects;

    private List<Role> roles;

    private List<SalaryResponse> salaries;

    private List<TeachingHoursResponse> teachingHoursResponses;



    public static UserResponseDto from(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .registeredDate(user.getRegisteredDate().toString())
                .fullName(user.getFullName())
                .dailyLessons(user.getDailyLessons())
                .workExperiences(WorkExperienceDto.toAllResponse(user.getWorkExperiences()))
                .achievements(AchievementDto.toAllResponse(user.getAchievements()))
                .teachingHoursResponses(TeachingHoursResponse.toAllResponse(user.getTeachingHours()))
                .biography(user.getBiography())
                .studentClass(StudentClassDto.toResponse(user.getStudentClass()))
                .salaries(SalaryResponse.toAllResponse(user.getSalaries()))
                .inn(user.getInn())
                .inps(user.getInps())
                .subjects(user.getSubjects())
                .roles(user.getRoles())
                .phoneNumber(user.getPhoneNumber())
                .birthDate(user.getBirthDate().toString())
                .gender(user.getGender().toString())
                .build();
    }
}
