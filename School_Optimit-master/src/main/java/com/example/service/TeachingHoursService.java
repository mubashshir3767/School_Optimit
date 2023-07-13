package com.example.service;

import com.example.entity.TeachingHours;
import com.example.entity.TypeOfWork;
import com.example.enums.Constants;
import com.example.exception.RecordAlreadyExistException;
import com.example.exception.RecordNotFoundException;
import com.example.model.common.ApiResponse;
import com.example.model.request.TeachingHoursRequest;
import com.example.model.response.TeachingHoursResponse;
import com.example.repository.StudentClassRepository;
import com.example.repository.TeachingHoursRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TeachingHoursService implements BaseService<TeachingHoursRequest, Integer> {

    private final TeachingHoursRepository teachingHoursRepository;
    private final StudentClassRepository studentClassRepository;
    private final TypeOfWorkService typeOfWorkService;
    private final UserService userService;

    @Override
    public ApiResponse create(TeachingHoursRequest teachingHoursRequest) {
        TeachingHours teachingHours = TeachingHours.toTeachingHours(teachingHoursRequest);
        checkIsAlready(teachingHoursRequest, teachingHours);
        set(teachingHoursRequest, teachingHours);
        teachingHoursRepository.save(teachingHours);
        return new ApiResponse(Constants.SUCCESSFULLY, true);
    }

    public ApiResponse incrementHours(TeachingHoursRequest teachingHoursRequest) {
        TeachingHours teachingHours = getTeaching(teachingHoursRequest);
        teachingHours.setLessonHours(teachingHours.getLessonHours() + 1);
        teachingHours.getClassIds().add(teachingHoursRequest.getClassId());
        teachingHoursRepository.save(teachingHours);
        return new ApiResponse(Constants.SUCCESSFULLY, true, TeachingHoursResponse.teachingHoursDTO(teachingHours));
    }

    public ApiResponse decrementHours(TeachingHoursRequest teachingHoursRequest) {
        TeachingHours teachingHours = getTeaching(teachingHoursRequest);
        if (teachingHours.getLessonHours() == 0) {
            throw new RecordNotFoundException(Constants.HOURS_NOT_ENOUGH);
        }
        teachingHours.getClassIds().remove(teachingHoursRequest.getClassId());
        teachingHours.setLessonHours(teachingHours.getLessonHours() - 1);
        teachingHoursRepository.save(teachingHours);
        return new ApiResponse(Constants.SUCCESSFULLY, true, TeachingHoursResponse.teachingHoursDTO(teachingHours));
    }

    private TeachingHours getTeaching(TeachingHoursRequest teachingHoursRequest) {
        TypeOfWork typeOfWork = typeOfWorkService.checkById(teachingHoursRequest.getTypeOfWorkId());
        return teachingHoursRepository.findByTeacherIdAndDateAndTypeOfWork(teachingHoursRequest.getTeacherId(), toLocalDate(teachingHoursRequest.getDate()), typeOfWork).orElseThrow(() -> new RecordNotFoundException(Constants.TEACHING_HOURS_NOT_FOUND));
    }

    @Override
    public ApiResponse getById(Integer integer) {
        return new ApiResponse(checkById(integer), true);
    }

    public ApiResponse getAll() {
        List<TeachingHoursResponse> all = new ArrayList<>();
        toAllHours(all, teachingHoursRepository.findAll());
        return new ApiResponse(Constants.SUCCESSFULLY, true, all);
    }

    public ApiResponse getByTeacherId(Integer id) {
        List<TeachingHoursResponse> all = new ArrayList<>();
        toAllHours(all, teachingHoursRepository.findAllByTeacherId(id));
        return new ApiResponse(Constants.FOUND, true, all);
    }

    public ApiResponse getByTeacherIdAndDate(Integer id, String date) {
        List<TeachingHoursResponse> all = new ArrayList<>();
        toAllHours(all, teachingHoursRepository.findAllByTeacherIdAndDate(id, toLocalDate(date)));
        return new ApiResponse(Constants.FOUND, true, all);
    }

    @Override
    public ApiResponse update(TeachingHoursRequest teachingHoursRequest) {
        checkById(teachingHoursRequest.getId());
        TeachingHours teachingHours = getTeachingHours(teachingHoursRequest);
        teachingHoursRepository.save(teachingHours);
        return new ApiResponse(teachingHours, true);
    }

    private TeachingHours getTeachingHours(TeachingHoursRequest teachingHoursRequest) {
        TeachingHours teachingHours = TeachingHours.toTeachingHours(teachingHoursRequest);
//        checkClassId(teachingHoursRequest);
        teachingHours.setClassIds(teachingHoursRequest.getClassesIds());
        teachingHours.setTeacher(userService.checkUserExistById(teachingHoursRequest.getTeacherId()));
        teachingHours.setDate(toLocalDate(teachingHoursRequest.getDate()));
        teachingHours.setTypeOfWork(typeOfWorkService.checkById(teachingHoursRequest.getTypeOfWorkId()));
        teachingHours.setId(teachingHoursRequest.getId());
        return teachingHours;
    }

    @Override
    public ApiResponse delete(Integer integer) {
        TeachingHoursResponse teachingHoursResponse = checkById(integer);
        teachingHoursRepository.deleteById(integer);
        return new ApiResponse(Constants.DELETED, true, teachingHoursResponse);
    }

    private void set(TeachingHoursRequest teachingHoursRequest, TeachingHours teachingHours) {
        teachingHours.setTeacher(userService.checkUserExistById(teachingHoursRequest.getTeacherId()));
        teachingHours.setDate(toLocalDate(teachingHoursRequest.getDate()));
        teachingHours.setTypeOfWork(typeOfWorkService.checkById(teachingHoursRequest.getTypeOfWorkId()));
    }

    private void checkIsAlready(TeachingHoursRequest teachingHoursRequest, TeachingHours teachingHours) {
        if (teachingHoursRepository.findByTeacherIdAndDateAndTypeOfWork(teachingHoursRequest.getTeacherId(), toLocalDate(teachingHoursRequest.getDate()), teachingHours.getTypeOfWork()).isPresent()) {
            throw new RecordAlreadyExistException(Constants.TEACHING_HOURS_ALREADY_EXIST_THIS_DATE);
        }
    }

    private void toAllHours(List<TeachingHoursResponse> all, List<TeachingHours> teachingHoursResponses) {
        teachingHoursResponses.forEach(teachingHours -> {
            all.add(TeachingHoursResponse.teachingHoursDTO(teachingHours));
        });
    }

    private void checkClassId(TeachingHoursRequest teachingHoursRequest) {
        studentClassRepository.findById(teachingHoursRequest.getClassId()).orElseThrow(() -> new RecordNotFoundException(Constants.CLASS_NOT_FOUND));
    }

    private LocalDate toLocalDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date, formatter);
    }

    private TeachingHoursResponse checkById(Integer integer) {
        TeachingHours teachingHours = teachingHoursRepository.findById(integer).orElseThrow(() -> new RecordNotFoundException(Constants.TEACHING_HOURS_NOT_FOUND));
        return TeachingHoursResponse.teachingHoursDTO(teachingHours);
    }
}
