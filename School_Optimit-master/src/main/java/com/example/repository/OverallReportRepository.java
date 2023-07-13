package com.example.repository;

import com.example.entity.OverallReport;
import com.example.enums.Months;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OverallReportRepository extends JpaRepository<OverallReport,Integer> {

    OverallReport findByIdAndMonth(Integer integer, Months months);
}
