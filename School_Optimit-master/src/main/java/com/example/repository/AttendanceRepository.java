//package com.example.repository;
//
//import com.example.entity.Attendance;
//import com.example.entity.Score;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.UUID;
//
//public interface AttendanceRepository extends JpaRepository<Attendance, UUID> {
//
//    List<Attendance> findAllByJournalIdAndCreatedDateBetween(Integer journalId, LocalDateTime createdDate, LocalDateTime createdDate2);
//    List<Attendance> findAllByJournalId(Integer journalId);
//
//    Page<Attendance> findAllByJournalIdAndStudentId(Integer id, Integer studentId, Pageable pageable);
//}
