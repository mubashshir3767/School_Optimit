package com.example.service;

import com.example.entity.*;
import com.example.exception.RecordAlreadyExistException;
import com.example.exception.RecordNotFoundException;
import com.example.model.common.ApiResponse;
import com.example.model.request.JournalRequestDto;
import com.example.model.response.JournalResponseDto;
import com.example.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

import static com.example.enums.Constants.*;

@Service
@RequiredArgsConstructor
public class JournalService implements BaseService<JournalRequestDto, Integer> {

    private final JournalRepository journalRepository;
    private final StudentClassRepository studentClassRepository;
    private final BranchRepository branchRepository;
//    private final AttendanceRepository attendanceRepository;
    private final ScoreRepository scoreRepository;
    private final SubjectRepository subjectRepository;


    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public ApiResponse create(JournalRequestDto dto) {
        if (journalRepository.existsByStudentClassIdAndActiveTrue(dto.getStudentClassId())) {
            throw new RecordAlreadyExistException(CLASS_ALREADY_HAVE_JOURNAL);
        }
        Branch branch = branchRepository.findById(dto.getBranchId()).orElseThrow(() -> new RecordNotFoundException(BRANCH_NOT_FOUND));
        StudentClass studentClass = studentClassRepository.findById(dto.getStudentClassId()).orElseThrow(() -> new RecordNotFoundException(CLASS_NOT_FOUND));
        List<Subject> subjects = new ArrayList<>();
        dto.getSubjectIdList().forEach(id -> {
            subjects.add(subjectRepository.findById(id).get());
        });
        Journal journal = Journal.builder()
                .active(true)
                .branch(branch)
                .studentClass(studentClass)
                .subjectList(subjects)
                .build();
        journalRepository.save(journal);
        return new ApiResponse(SUCCESSFULLY, true);
    }
    @ResponseStatus(HttpStatus.OK)
    @Override
    public ApiResponse getById(Integer integer) {
        Journal journal = journalRepository.findById(integer).orElseThrow(() -> new RecordNotFoundException(JOURNAL_NOT_FOUND));
        List<Score> scoreList = scoreRepository.findAllByJournalId(journal.getId());
//        List<Attendance> attendanceList = attendanceRepository.findAllByJournalId(journal.getId());
        JournalResponseDto journalResponseDto = JournalResponseDto.builder()
                .journal(journal)
//                .attendanceList(attendanceList)
                .scoreList(scoreList)
                .build();
        return new ApiResponse(journalResponseDto, true);
    }
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getAllByIdBranchId(Integer integer) {
        List<Journal> journalList = journalRepository.findAllByBranchIdAndActiveTrue(integer);
        return new ApiResponse(journalList, true);
    }

    @ResponseStatus(HttpStatus.OK)
    @Override
    public ApiResponse update(JournalRequestDto dto) {
        if (journalRepository.existsByStudentClassIdAndActiveTrue(dto.getStudentClassId())) {
            throw new RecordAlreadyExistException(CLASS_ALREADY_HAVE_JOURNAL);
        }
        Journal journal = journalRepository.findById(dto.getId()).orElseThrow(() -> new RecordNotFoundException(JOURNAL_NOT_FOUND));
        StudentClass studentClass = studentClassRepository.findById(dto.getStudentClassId()).orElseThrow(() -> new RecordNotFoundException(CLASS_NOT_FOUND));
        journal.setStudentClass(studentClass);
        dto.getSubjectIdList().forEach(id -> {
            journal.getSubjectList().add(subjectRepository.findById(id).get());
        });
        journalRepository.save(journal);
        return new ApiResponse(SUCCESSFULLY, true);
    }
    @ResponseStatus(HttpStatus.OK)
    @Override
    public ApiResponse delete(Integer integer) {
        Journal journal = journalRepository.findById(integer).orElseThrow(() -> new RecordNotFoundException(JOURNAL_NOT_FOUND));
        journal.setActive(false);
        journalRepository.save(journal);
        return new ApiResponse(DELETED, true);
    }
}
