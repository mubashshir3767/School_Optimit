package com.example.repository;

import com.example.entity.Score;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ScoreRepository extends JpaRepository<Score, UUID> {

    Page<Score> findAllByJournalIdAndTeacherIdAndSubjectId(Integer journalId, Integer teacherId, Integer subjectId, Pageable pageable);

    List<Score> findAllByJournalIdAndTeacherIdAndSubjectIdAndCreatedDateBetween(Integer journalId, Integer teacherId, Integer subjectId, LocalDateTime startWeek, LocalDateTime endWeek);

    List<Score> findAllByJournalId(Integer journalId);
    List<Score> findAllByStudentIdAndCreatedDateBetween(Integer studentId, LocalDateTime createdDate, LocalDateTime createdDate2);

    Page<Score> findAllByJournalIdAndSubjectIdAndStudentId(Integer journalId, Integer subjectId, Integer studentId, Pageable pageable);

    List<Score> findAllByCreatedDateBetween(LocalDateTime createdDate, LocalDateTime createdDate2);

}
