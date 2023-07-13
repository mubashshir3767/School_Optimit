package com.example.service;

import com.example.entity.User;
import com.example.entity.WorkExperience;
import com.example.enums.Constants;
import com.example.exception.RecordAlreadyExistException;
import com.example.exception.RecordNotFoundException;
import com.example.exception.UserNotFoundException;
import com.example.model.common.ApiResponse;
import com.example.model.request.WorkExperienceDto;
import com.example.repository.UserRepository;
import com.example.repository.WorkExperienceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkExperienceService implements BaseService<WorkExperienceDto, Integer> {

    private final WorkExperienceRepository workExperienceRepository;
    private final UserRepository userRepository;

    @Override
    public ApiResponse create(WorkExperienceDto workExperienceDto) {
        checkIfExist(workExperienceDto);
        WorkExperience workExperience = WorkExperience.toWorkExperience(workExperienceDto);
        workExperience.setEmployee(userRepository.findById(workExperienceDto.getEmployeeId()).orElseThrow(()->new UserNotFoundException(Constants.USER_NOT_FOUND)));
        setEmployee(workExperienceDto, workExperience);
        workExperienceRepository.save(workExperience);
        return new ApiResponse(Constants.SUCCESSFULLY, true);
    }

    private void setEmployee(WorkExperienceDto workExperienceDto, WorkExperience workExperience) {
        User user = userRepository.findById(workExperienceDto.getEmployeeId()).orElseThrow(() -> new RecordNotFoundException(Constants.USER_NOT_FOUND));
        workExperience.setEmployee(user);
    }

    @Override
    public ApiResponse getById(Integer id) {
        return new ApiResponse(checkById(id), true);
    }

    @Override
    public ApiResponse update(WorkExperienceDto workExperienceDto) {
        checkById(workExperienceDto.getId());
        WorkExperience experience = WorkExperience.toWorkExperience(workExperienceDto);
        experience.setEmployee(userRepository.findById(workExperienceDto.getEmployeeId()).orElseThrow(()->new UserNotFoundException(Constants.USER_NOT_FOUND)));
        experience.setId(workExperienceDto.getId());
        workExperienceRepository.save(experience);
        return new ApiResponse(Constants.SUCCESSFULLY, true);
    }

    @Override
    public ApiResponse delete(Integer id) {
        WorkExperienceDto workExperience = checkById(id);
        workExperienceRepository.deleteById(id);
        return new ApiResponse(Constants.DELETED, true, workExperience);
    }

    public WorkExperienceDto checkById(Integer id) {
        return WorkExperienceDto.toWorkExperienceDto(workExperienceRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(Constants.WORK_EXPERIENCE_NOT_FOUND)));
    }

    public List<WorkExperience> checkAllById(List<Integer> workExperiences) {
        return workExperienceRepository.findAllById(workExperiences);
    }

    public List<WorkExperience> getAllByUserId(Integer id) {
        return workExperienceRepository.findAllByEmployee(id);
    }

    private void checkIfExist(WorkExperienceDto workExperienceDto) {
        boolean present = workExperienceRepository.findByPlaceOfWorkAndPositionAndEmployeeId(workExperienceDto.getPlaceOfWork(), workExperienceDto.getPosition(),workExperienceDto.getEmployeeId()).isPresent();
        if (present) {
            throw new RecordAlreadyExistException(Constants.WORK_EXPERIENCE_ALREADY_EXIST);
        }
    }

    public List<WorkExperience> toAllEntity(List<WorkExperienceDto> workExperiences) {
        List<WorkExperience> workExperienceList = new ArrayList<>();
        for (WorkExperienceDto workExperience : workExperiences) {
            workExperienceList.add(WorkExperience.toWorkExperience(workExperience));
        }
        return workExperienceList;
    }

    public List<WorkExperience> saveAll(List<WorkExperience> allEntity) {
        return workExperienceRepository.saveAll(allEntity);
    }
}
