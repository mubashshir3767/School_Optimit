package com.example.service;

import com.example.entity.Score;
import com.example.entity.StudentBalance;
import com.example.exception.RecordNotFoundException;
import com.example.repository.ScoreRepository;
import com.example.repository.StudentBalanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.example.enums.Constants.STUDENT_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class ScheduleService {

    private final StudentBalanceRepository studentBalanceRepository;
    private final ScoreRepository scoreRepository;


        @Scheduled(cron = "22 14 * * *")
//    @Scheduled(cron = "40 23 * * 1-6")
    public void studentBalanceChecker() {
        LocalDateTime startTime = LocalDate.now().atStartOfDay();
        LocalDateTime localDateTime = startTime.plusHours(23);
        LocalDateTime endTime = localDateTime.plusMinutes(59);
        List<Score> all = scoreRepository.findAllByCreatedDateBetween(startTime, endTime);
        all.forEach(score -> {
            StudentBalance studentBalance = studentBalanceRepository.findByStudentId(score.getStudent().getId())
                    .orElseThrow(() -> new RecordNotFoundException(STUDENT_NOT_FOUND));
            studentBalance.setBalance(studentBalance.getBalance() - 110000);
            studentBalance.setUpdatedDate(LocalDateTime.now());
            studentBalanceRepository.save(studentBalance);
        });
    }

}
