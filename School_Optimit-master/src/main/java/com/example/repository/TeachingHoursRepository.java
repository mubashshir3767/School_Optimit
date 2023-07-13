package com.example.repository;

import com.example.entity.TeachingHours;
import com.example.entity.TypeOfWork;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TeachingHoursRepository extends JpaRepository<TeachingHours,Integer> {
    Optional<TeachingHours> findByTeacherIdAndDateAndTypeOfWork(Integer teacherId, LocalDate date, TypeOfWork typeOfWork);

    List<TeachingHours> findAllByTeacherId(Integer teacherId);
    List<TeachingHours> findAllByTeacherIdAndDate(Integer teacherId, LocalDate date);
}
