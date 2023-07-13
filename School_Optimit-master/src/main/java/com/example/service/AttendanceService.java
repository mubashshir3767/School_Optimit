//package com.example.service;
//
//import com.example.entity.Attendance;
//import com.example.entity.Journal;
//import com.example.entity.Score;
//import com.example.entity.Student;
//import com.example.exception.RecordNotFoundException;
//import com.example.exception.UserNotFoundException;
//import com.example.model.common.ApiResponse;
//import com.example.model.request.AttendanceRequestDto;
//import com.example.model.request.ScoreDto;
//import com.example.model.response.AttendanceResponse;
//import com.example.model.response.AttendanceResponseList;
//import com.example.model.response.ScoreResponseForStudent;
//import com.example.model.response.ScoreResponseList;
//import com.example.repository.AttendanceRepository;
//import com.example.repository.JournalRepository;
//import com.example.repository.StudentRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Service;
//import org.springframework.web.bind.annotation.ResponseStatus;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//
//import static com.example.enums.Constants.*;
//
//@Service
//@RequiredArgsConstructor
//public class AttendanceService implements BaseService<AttendanceRequestDto, UUID> {
//
//    private final AttendanceRepository attendanceRepository;
//    private final StudentRepository studentRepository;
//    private final JournalRepository journalRepository;
//
//    @Override
//    public ApiResponse create(AttendanceRequestDto dto) {
//        Student student = studentRepository.findById(dto.getStudentId())
//                .orElseThrow(() -> new UserNotFoundException(STUDENT_NOT_FOUND));
//        Journal journal = journalRepository.findById(dto.getJournalId())
//                .orElseThrow(() -> new UserNotFoundException(JOURNAL_NOT_FOUND));
//        Attendance attendance = Attendance.builder()
//                .createdDate(LocalDateTime.now())
//                .student(student)
//                .journal(journal)
//                .come(true)
//                .build();
//        attendanceRepository.save(attendance);
//        return new ApiResponse(SUCCESSFULLY, true);
//    }
//
//    @Override
//    public ApiResponse getById(UUID uuid) {
//        return null;
//    }
//
//    @Override
//    public ApiResponse update(AttendanceRequestDto dto) {
//        Attendance attendance = attendanceRepository.findById(dto.getId())
//                .orElseThrow(() -> new UserNotFoundException(ATTENDANT_NOT_FOUND));
//        attendance.setCome(false);
//        attendance.setUpdateDate(LocalDateTime.now());
//        attendanceRepository.save(attendance);
//        return new ApiResponse(SUCCESSFULLY, true);
//    }
//
//    @Override
//    public ApiResponse delete(UUID uuid) {
//        return null;
//    }
//
//    public ApiResponse getAll(Integer journalId) {
//        int dayOfWeek = LocalDate.now().atStartOfDay().getDayOfWeek().getValue();
//        LocalDateTime startWeek = LocalDateTime.now().minusDays(dayOfWeek);
//        LocalDateTime endWeek = startWeek.plusDays(7);
//        List<Attendance> all = attendanceRepository.findAllByJournalIdAndCreatedDateBetween(journalId, startWeek, endWeek);
//        List<AttendanceResponse> attendanceResponseList = new ArrayList<>();
//        all.forEach(attendance -> attendanceResponseList.add(AttendanceResponse.from(attendance)));
//        return new ApiResponse(attendanceResponseList, true);
//    }
//
//    @ResponseStatus(HttpStatus.OK)
//    public ApiResponse getForStudentAndFamily(ScoreDto scoreDto) {
//        Journal journal = journalRepository.findByStudentClassIdAndActiveTrue(scoreDto.getClassId())
//                .orElseThrow(() -> new RecordNotFoundException(JOURNAL_NOT_FOUND));
//        Pageable pageable = PageRequest.of(scoreDto.getPage(), scoreDto.getSize());
//        Page<Attendance> all = attendanceRepository.findAllByJournalIdAndStudentId(journal.getId(), scoreDto.getStudentId(), pageable);
//        List<AttendanceResponse> attendanceResponseList = new ArrayList<>();
//        all.getContent().forEach(attendance -> attendanceResponseList.add(AttendanceResponse.from(attendance)));
//        return new ApiResponse(new AttendanceResponseList(attendanceResponseList, all.getTotalElements(), all.getTotalPages(), all.getNumber()), true);
//    }
//}
