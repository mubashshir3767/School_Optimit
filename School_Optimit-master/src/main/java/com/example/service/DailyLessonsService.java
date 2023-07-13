package com.example.service;

import com.example.entity.DailyLessons;
import com.example.entity.StudentClass;
import com.example.enums.Constants;
import com.example.exception.RecordAlreadyExistException;
import com.example.exception.RecordNotFoundException;
import com.example.model.common.ApiResponse;
import com.example.model.request.DailyLessonsDto;
import com.example.model.request.DailyLessonsResponse;
import com.example.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DailyLessonsService implements BaseService<DailyLessonsDto, Integer> {

    private final DailyLessonsRepository dailyLessonsRepository;
    private final UserRepository userRepository;
    private final BranchRepository branchRepository;
    private final TypeOfWorkRepository typeOfWorkRepository;
    private final SubjectRepository subjectRepository;
    private final StudentClassRepository studentClassRepository;

    @Override
    public ApiResponse create(DailyLessonsDto dailySalaryReport) {
        DailyLessons dailyLessons = toDailyLessons(dailySalaryReport);
        if (dailyLessonsRepository.findFirstByActiveAndDayAndLessonTime(true, dailyLessons.getDay(), dailyLessons.getLessonTime()).isPresent()) {
            throw new RecordAlreadyExistException(Constants.DAILY_LESSON_ALREADY_EXIST);
        }
        dailyLessonsRepository.save(dailyLessons);
        return new ApiResponse(Constants.SUCCESSFULLY, true, DailyLessonsResponse.toResponse(dailyLessons));
    }

    @Override
    public ApiResponse getById(Integer integer) {
        DailyLessons dailyLessons = checkAndGetById(integer);
        return new ApiResponse(Constants.FOUND, true, DailyLessonsResponse.toResponse(dailyLessons));
    }

    public ApiResponse getAll() {
        return new ApiResponse(Constants.FOUND, true, DailyLessonsResponse.toAllResponse(dailyLessonsRepository.findAll()));
    }

    @Override
    public ApiResponse update(DailyLessonsDto dailyLessonsDto) {
        checkAndGetById(dailyLessonsDto.getId());
        DailyLessons dailyLessons = toDailyLessons(dailyLessonsDto);
        dailyLessons.setId(dailyLessonsDto.getId());
        return new ApiResponse(Constants.SUCCESSFULLY, true, DailyLessonsResponse.toResponse(dailyLessons));
    }

    @Override
    public ApiResponse delete(Integer integer) {
        DailyLessons dailyLessons = checkAndGetById(integer);
        dailyLessonsRepository.deleteById(integer);
        return new ApiResponse(Constants.DELETED, true, DailyLessonsResponse.toResponse(dailyLessons));
    }

    private DailyLessons checkAndGetById(Integer integer) {
        return dailyLessonsRepository.findById(integer).orElseThrow(() -> new RecordNotFoundException(Constants.DAILY_LESSON_NOT_FOUND));
    }

    public List<DailyLessons> checkAllById(List<Integer> dailyLessons) {
        return dailyLessonsRepository.findAllById(dailyLessons);
    }

    public DailyLessons toDailyLessons(DailyLessonsDto dailySalaryReport) {
        StudentClass studentClass = studentClassRepository.findById(dailySalaryReport.getStudentClassId()).orElseThrow(() -> new RecordNotFoundException(Constants.STUDENT_NOT_FOUND));
        return DailyLessons
                .builder()
                .lessonTime(dailySalaryReport.getLessonTime())
                .day(toLocalDate(dailySalaryReport.getDay()))
                .branch(branchRepository.findById(dailySalaryReport.getBranchId()).orElseThrow(() -> new RecordNotFoundException(Constants.BRANCH_NOT_FOUND)))
                .studentClass(studentClass)
                .subject(subjectRepository.findById(dailySalaryReport.getSubjectId()).orElseThrow(() -> new RecordNotFoundException(Constants.SUBJECT_NOT_FOUND)))
                .teacher(userRepository.findById(dailySalaryReport.getTeacherId()).orElseThrow(() -> new RecordNotFoundException(Constants.USER_NOT_FOUND)))
                .typeOfWork(typeOfWorkRepository.findById(dailySalaryReport.getTypeOfWorkId()).orElseThrow(() -> new RecordNotFoundException(Constants.TYPE_OF_WORK_NOT_FOUND)))
                .room(studentClass.getRoom())
                .active(true)
                .build();
    }

    public LocalDate toLocalDate(String date) {
        try {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return LocalDate.parse(date, dateTimeFormatter);
        } catch (Exception e) {
            throw new RecordNotFoundException(Constants.DATE_DO_NOT_MATCH + "  " + e);
        }
    }
}
