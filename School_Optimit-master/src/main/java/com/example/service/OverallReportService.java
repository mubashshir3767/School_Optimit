package com.example.service;

import com.example.entity.OverallReport;
import com.example.entity.Salary;
import com.example.entity.StudentClass;
import com.example.entity.User;
import com.example.enums.Constants;
import com.example.enums.Months;
import com.example.exception.RecordNotFoundException;
import com.example.model.common.ApiResponse;
import com.example.model.request.OverallReportRequest;
import com.example.model.response.OverallReportResponse;
import com.example.repository.OverallReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class OverallReportService implements BaseService<OverallReportRequest, Integer> {

    private final OverallReportRepository overallReportRepository;
    private final UserService userService;

    @Override
    public ApiResponse create(OverallReportRequest overallReportRequest) {
        User user = userService.checkUserExistById(overallReportRequest.getUserId());
        OverallReport overallReport = getOverallReport(user);
        setSalary(user, overallReport);
        overallReport.setMonth(overallReportRequest.getMonth());
        overallReportRepository.save(overallReport);
        return new ApiResponse(Constants.SUCCESSFULLY, true, OverallReportResponse.toOverallResponse(overallReport));
    }

    @Override
    public ApiResponse getById(Integer integer) {
        OverallReport overallReport = checkById(integer);
        OverallReportResponse overallReportResponse = OverallReportResponse.toOverallResponse(overallReport);
        return new ApiResponse(Constants.SUCCESSFULLY, true, overallReportResponse);
    }

    public ApiResponse getAll() {
        List<OverallReportResponse> allOverallResponse = OverallReportResponse.toAllOverallResponse(overallReportRepository.findAll());
        return new ApiResponse(Constants.SUCCESSFULLY, true, allOverallResponse);
    }

    public ApiResponse getByIdAndMonth(Integer integer, Months months) {
        OverallReport overallReport = overallReportRepository.findByIdAndMonth(integer, months);
        OverallReportResponse overallReportResponse = OverallReportResponse.toOverallResponse(overallReport);
        return new ApiResponse(Constants.SUCCESSFULLY, true, overallReportResponse);
    }

    @Override
    public ApiResponse update(OverallReportRequest overallReportRequest) {
        checkById(overallReportRequest.getId());
        User user = userService.checkUserExistById(overallReportRequest.getUserId());
        OverallReport overallReport = getOverallReport(user);
        overallReportRepository.save(overallReport);
        return new ApiResponse(Constants.SUCCESSFULLY, true, OverallReportResponse.toOverallResponse(overallReport));
    }

    @Override
    public ApiResponse delete(Integer integer) {
        OverallReport overallReport = checkById(integer);
        overallReportRepository.deleteById(integer);
        return new ApiResponse(Constants.DELETED, true, OverallReportResponse.toOverallResponse(overallReport));
    }

    private void setSalary(User user, OverallReport overallReport) {
        for (Salary salary : user.getSalaries()) {
            if (salary.isActive()) {
                overallReport.setSalary(salary);
            }
        }
    }

    private OverallReport checkById(Integer integer) {
        return overallReportRepository.findById(integer).orElseThrow(() -> new RecordNotFoundException(Constants.OVERALL_REPORT_NOT_FOUND));
    }

    private OverallReport getOverallReport(User user) {
        String name = " ";
        if (user.getStudentClass() != null) {
            name = user.getStudentClass().getClassName();
        }
        try {
            return OverallReport
                    .builder()
                    .branch(user.getBranch())
                    .position(user.getPosition())
                    .classLeadership(name)
                    .user(user)
                    .build();
        } catch (Exception e) {
            throw new RecordNotFoundException(Constants.SOMETHING_IS_WRONG);
        }
    }
}
