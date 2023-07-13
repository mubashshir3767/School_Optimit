package com.example.controller;

import com.example.model.common.ApiResponse;
import com.example.model.request.SalaryRequest;
import com.example.service.SalaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/salary/")
public class SalaryController {

    private final SalaryService salaryService;

    @PostMapping("save")
    public ApiResponse save(@RequestBody SalaryRequest salaryRequest) {
        return salaryService.create(salaryRequest);
    }

    @GetMapping("giveCashAdvance/{salaryId}/{cashSalary}/{paymentTypeId}")
    public ApiResponse giveCashAdvance(@PathVariable Integer salaryId,
                                       @PathVariable double cashSalary,
                                       @PathVariable Integer paymentTypeId) {
        return salaryService.giveCashAdvance(salaryId, cashSalary, paymentTypeId);
    }

    @GetMapping("takeDebitAmount/{salaryId}/{debitAmount}")
    public ApiResponse takeDebitAmount(@PathVariable Integer salaryId,
                                       @PathVariable double debitAmount) {
        return salaryService.takeDebitAmount(salaryId, debitAmount);
    }

    @GetMapping("givePartlySalary/{salaryId}/{partlySalary}/{paymentTypeId}")
    public ApiResponse givePartlySalary(@PathVariable Integer salaryId,
                                        @PathVariable double partlySalary,
                                        @PathVariable Integer paymentTypeId) {
        return salaryService.givePartlySalary(salaryId, partlySalary, paymentTypeId);
    }

    @GetMapping("getCurrentMonthFixSalary/{fromDate}/{toDate}/{salaryId}")
    public ApiResponse getCurrentMonthFixSalary(@PathVariable String fromDate,
                                                @PathVariable String toDate,
                                                @PathVariable Integer salaryId) {
        return salaryService.getCurrentMonthFixSalary(fromDate, toDate, salaryId);
    }


    @GetMapping("currentMonthSalaryAmount/{salaryId}")
    public ApiResponse giveRemainSalary(@PathVariable Integer salaryId) {
        return salaryService.currentMonthSalaryAmount(salaryId);
    }

    @GetMapping("giveSalary/{salaryId}/{salary},/{withholdingOfDebtIfAny}/{paymentTypeId}")
    public ApiResponse giveSalary(@PathVariable Integer salaryId,
                                  @PathVariable double salary,
                                  @PathVariable boolean withholdingOfDebtIfAny,
                                  @PathVariable Integer paymentTypeId) {
        return salaryService.giveSalary(salaryId, salary, withholdingOfDebtIfAny, paymentTypeId);
    }

    @GetMapping("getCurrentMonthTeachingHoursSalary/{salaryId}")
    public ApiResponse getCurrentMonthTeachingHoursSalary(@PathVariable Integer salaryId) {
        return salaryService.getCurrentMonthTeachingHoursSalary(salaryId);
    }

    @GetMapping("getById/{id}")
    public ApiResponse getById(@PathVariable Integer id) {
        return salaryService.getById(id);
    }

    @PutMapping("update")
    public ApiResponse update(@RequestBody SalaryRequest salaryRequest) {
        return salaryService.update(salaryRequest);
    }

    @DeleteMapping("delete/{id}")
    public ApiResponse delete(@PathVariable Integer id) {
        return salaryService.delete(id);
    }
}
