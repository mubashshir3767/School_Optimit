package com.example.service;

import com.example.entity.*;
import com.example.exception.RecordNotFoundException;
import com.example.model.common.ApiResponse;
import com.example.model.request.LessonScheduleDto;
import com.example.model.response.ErrorResponseSchedule;
import com.example.model.response.LessonScheduleResponse;
import com.example.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.enums.Constants.*;

@Service
@RequiredArgsConstructor
public class LessonScheduleService implements BaseService<List<LessonScheduleDto>, Integer> {

    private final BranchRepository branchRepository;
    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;
    private final RoomRepository roomRepository;
    private final StudentClassRepository studentClassRepository;
    private final LessonScheduleRepository lessonScheduleRepository;

    @Override
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse create(List<LessonScheduleDto> scheduleDtoList) {
        List<ErrorResponseSchedule> errorResponse = new ArrayList<>();
        int i = 0;
        for (LessonScheduleDto schd : scheduleDtoList) {
            Optional<LessonSchedule> teacherBusy = lessonScheduleRepository.findFirstByBranchIdAndTeacherIdAndStartTimeAndActiveTrue(schd.getBranchId(), schd.getTeacherId(), schd.getStartTime());
            Optional<LessonSchedule> studentClassBusy = lessonScheduleRepository.findFirstByBranchIdAndStudentClassIdAndStartTimeAndActiveTrue(schd.getBranchId(), schd.getStudentClassId(), schd.getStartTime());
            Optional<LessonSchedule> roomBusy = lessonScheduleRepository.findFirstByBranchIdAndRoomIdAndStartTimeAndActiveTrue(schd.getBranchId(), schd.getRoomId(), schd.getStartTime());
            if (teacherBusy.isPresent()) {
                errorResponse.add(new ErrorResponseSchedule(schd, "teacher busy this time"));
                if (studentClassBusy.isPresent()) {
                    errorResponse.get(i).setMassage("teacher and class busy this time");
                }
                if (roomBusy.isPresent()) {
                    errorResponse.get(i).setMassage("teacher , class and room busy this time");
                }
            } else if (studentClassBusy.isPresent()) {
                errorResponse.add(new ErrorResponseSchedule(schd, "class busy this time"));
                if (roomBusy.isPresent()) {
                    errorResponse.get(i).setMassage("class and room busy this time");
                }
            } else if (roomBusy.isPresent()) {
                errorResponse.add(new ErrorResponseSchedule(schd, "room busy this time"));
            } else {
                errorResponse.add(new ErrorResponseSchedule(schd, "Everything ok"));
            }
            i++;

        }
        for (ErrorResponseSchedule errorResponseSchedule : errorResponse) {
            if (!errorResponseSchedule.getMassage().equals("Everything ok")) {
                return new ApiResponse(errorResponse, false);
            }
        }
        add(scheduleDtoList);
        return new ApiResponse(SUCCESSFULLY, true);
    }


    @Override
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getById(Integer integer) {
        LessonSchedule lessonSchedule = lessonScheduleRepository.findById(integer)
                .orElseThrow(() -> new RecordNotFoundException(LESSON_SCHEDULE_NOT_FOUND));
        return new ApiResponse(LessonScheduleResponse.from(lessonSchedule), true);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse update(List<LessonScheduleDto> scheduleDtoList) {
        return null;
    }


    @ResponseStatus(HttpStatus.OK)
    public ApiResponse update(LessonScheduleDto schd) {
        Optional<LessonSchedule> teacherBusy = lessonScheduleRepository.findFirstByBranchIdAndTeacherIdAndStartTimeAndActiveTrue(schd.getBranchId(), schd.getTeacherId(), schd.getStartTime());
        Optional<LessonSchedule> studentClassBusy = lessonScheduleRepository.findFirstByBranchIdAndStudentClassIdAndStartTimeAndActiveTrue(schd.getBranchId(), schd.getStudentClassId(), schd.getStartTime());
        Optional<LessonSchedule> roomBusy = lessonScheduleRepository.findFirstByBranchIdAndRoomIdAndStartTimeAndActiveTrue(schd.getBranchId(), schd.getRoomId(), schd.getStartTime());
        ErrorResponseSchedule errorResponse;
        if (teacherBusy.isPresent()) {
            errorResponse = new ErrorResponseSchedule(schd, "teacher busy this time");
            if (studentClassBusy.isPresent()) {
                errorResponse = new ErrorResponseSchedule(schd, "teacher and class busy this time");
            }
            if (roomBusy.isPresent()) {
                errorResponse = new ErrorResponseSchedule(schd, "teacher , class and room busy this time");
            }
        } else if (studentClassBusy.isPresent()) {
            errorResponse = new ErrorResponseSchedule(schd, "class busy this time");
            if (roomBusy.isPresent()) {
                errorResponse = new ErrorResponseSchedule(schd, "class and room busy this time");
            }
        } else if (roomBusy.isPresent()) {
            errorResponse = new ErrorResponseSchedule(schd, "room busy this time");
        } else {
            errorResponse = new ErrorResponseSchedule(schd, "Everything ok");
        }

        if (!errorResponse.getMassage().equals("Everything ok")) {
            return new ApiResponse(errorResponse, false);
        }
        updateCreator(schd);
        return new ApiResponse(SUCCESSFULLY, true);
    }


    @Override
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse delete(Integer integer) {
        LessonSchedule lessonSchedule = lessonScheduleRepository.findById(integer)
                .orElseThrow(() -> new RecordNotFoundException(LESSON_SCHEDULE_NOT_FOUND));
        lessonSchedule.setActive(false);
        lessonScheduleRepository.save(lessonSchedule);
        return new ApiResponse(DELETED, true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getAllByBranchId(Integer integer) {
        int dayOfWeek = LocalDate.now().atStartOfDay().getDayOfWeek().getValue();
        LocalDateTime startWeek = LocalDateTime.now().minusDays(dayOfWeek);
        LocalDateTime endWeek = startWeek.plusDays(7);
        List<LessonSchedule> lessonScheduleList = lessonScheduleRepository
                .findAllByBranchIdAndActiveTrueAndStartTimeBetween(integer, startWeek, endWeek);
        List<LessonScheduleResponse> lessonScheduleResponseList = new ArrayList<>();
        lessonScheduleList.forEach(schedule -> lessonScheduleResponseList.add(LessonScheduleResponse.from(schedule)));
        return new ApiResponse(lessonScheduleResponseList, true);
    }

    private void add(List<LessonScheduleDto> scheduleDtoList) {
        List<LessonSchedule> lessonScheduleList = new ArrayList<>();
        scheduleDtoList.forEach(schd -> {
            Branch branch = branchRepository.findById(schd.getBranchId()).orElseThrow(() -> new RecordNotFoundException(BRANCH_NOT_FOUND));
            User teacher = userRepository.findById(schd.getTeacherId()).orElseThrow(() -> new RecordNotFoundException(TEACHER_NOT_FOUND));
            Subject subject = subjectRepository.findById(schd.getSubjectId()).orElseThrow(() -> new RecordNotFoundException(SUBJECT_NOT_FOUND));
            Room room = roomRepository.findById(schd.getRoomId()).orElseThrow(() -> new RecordNotFoundException(ROOM_NOT_FOUND));
            StudentClass studentClass = studentClassRepository.findById(schd.getStudentClassId()).orElseThrow(() -> new RecordNotFoundException(CLASS_NOT_FOUND));

            LessonSchedule lessonSchedule = LessonSchedule.from(schd);
            lessonSchedule.setBranch(branch);
            lessonSchedule.setTeacher(teacher);
            lessonSchedule.setSubject(subject);
            lessonSchedule.setRoom(room);
            lessonSchedule.setStudentClass(studentClass);
            lessonScheduleList.add(lessonSchedule);
        });
        lessonScheduleRepository.saveAll(lessonScheduleList);
    }

    private void updateCreator(LessonScheduleDto schd) {
        LessonSchedule lessonSchedule = lessonScheduleRepository.findById(schd.getId())
                .orElseThrow(() -> new RecordNotFoundException(LESSON_SCHEDULE_NOT_FOUND));

        Branch branch = branchRepository.findById(schd.getBranchId()).orElseThrow(() -> new RecordNotFoundException(BRANCH_NOT_FOUND));
        User teacher = userRepository.findById(schd.getTeacherId()).orElseThrow(() -> new RecordNotFoundException(TEACHER_NOT_FOUND));
        Subject subject = subjectRepository.findById(schd.getSubjectId()).orElseThrow(() -> new RecordNotFoundException(SUBJECT_NOT_FOUND));
        Room room = roomRepository.findById(schd.getRoomId()).orElseThrow(() -> new RecordNotFoundException(ROOM_NOT_FOUND));
        StudentClass studentClass = studentClassRepository.findById(schd.getStudentClassId()).orElseThrow(() -> new RecordNotFoundException(CLASS_NOT_FOUND));

        lessonSchedule.setDurationLesson(schd.getDurationLesson());
        lessonSchedule.setStartTime(schd.getStartTime());
        lessonSchedule.setEndTime(schd.getEndTime());
        lessonSchedule.setBranch(branch);
        lessonSchedule.setTeacher(teacher);
        lessonSchedule.setSubject(subject);
        lessonSchedule.setRoom(room);
        lessonSchedule.setStudentClass(studentClass);

        lessonScheduleRepository.save(lessonSchedule);
    }
}
