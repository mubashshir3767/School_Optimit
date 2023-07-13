package com.example.repository;

import com.example.entity.DailyLessons;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface DailyLessonsRepository extends JpaRepository<DailyLessons,Integer> {

    Optional<DailyLessons> findFirstByActiveAndDayAndLessonTime(boolean active, LocalDate day, int lessonTime);
}
