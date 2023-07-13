package com.example.repository;

import com.example.entity.LessonSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface LessonScheduleRepository  extends JpaRepository<LessonSchedule ,Integer> {

    Optional<LessonSchedule> findFirstByBranchIdAndTeacherIdAndStartTimeAndActiveTrue(Integer branchId, Integer teacherId, LocalDateTime startTime);
    Optional<LessonSchedule> findFirstByBranchIdAndStudentClassIdAndStartTimeAndActiveTrue(Integer branchId, Integer studentClassId, LocalDateTime startTime);
    Optional<LessonSchedule> findFirstByBranchIdAndRoomIdAndStartTimeAndActiveTrue(Integer branchId, Integer roomId, LocalDateTime startTime);
    List<LessonSchedule> findAllByBranchIdAndActiveTrueAndStartTimeBetween(Integer branchId, LocalDateTime startTime, LocalDateTime startTime2);
}
