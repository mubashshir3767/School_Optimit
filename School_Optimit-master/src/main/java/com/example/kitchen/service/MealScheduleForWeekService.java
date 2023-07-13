package com.example.kitchen.service;

import com.example.entity.Branch;
import com.example.exception.RecordNotFoundException;
import com.example.kitchen.entity.MealScheduleForDay;
import com.example.kitchen.entity.MealScheduleForWeek;
import com.example.kitchen.model.MealScheduleForWeekDto;
import com.example.kitchen.repository.MealScheduleForWeekRepository;
import com.example.kitchen.repository.MealScheduleForDayRepository;
import com.example.model.common.ApiResponse;
import com.example.repository.BranchRepository;
import com.example.service.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.example.enums.Constants.*;

@Service
@RequiredArgsConstructor
public class MealScheduleForWeekService implements BaseService<MealScheduleForWeekDto, Integer> {

    private final MealScheduleForWeekRepository mealScheduleForWeekRepository;
    private final BranchRepository branchRepository;
    private final MealScheduleForDayRepository mealScheduleForDayRepository;


    @Override
    public ApiResponse create(MealScheduleForWeekDto dto) {
        Branch branch = branchRepository.findById(dto.getBranchId()).orElseThrow(() -> new RecordNotFoundException(BRANCH_NOT_FOUND));
        List<MealScheduleForDay> mealScheduleForDayList = new ArrayList<>();
        dto.getMealScheduleIdList().forEach(obj -> {
            mealScheduleForDayList.add(mealScheduleForDayRepository.findById(obj).orElseThrow(() -> new RecordNotFoundException(MEAL_NOT_FOUND)));
        });
        MealScheduleForWeek mealScheduleForWeek = MealScheduleForWeek.builder()
                .startDay(dto.getStartDay())
                .endDay(dto.getEndDay())
                .branch(branch)
                .mealScheduleForDayList(mealScheduleForDayList)
                .active(true)
                .build();
        mealScheduleForWeekRepository.save(mealScheduleForWeek);
        return new ApiResponse(SUCCESSFULLY, true);
    }

    @Override
    public ApiResponse getById(Integer integer) {
        List<MealScheduleForWeek> mealScheduleForWeeks = mealScheduleForWeekRepository.findAllByBranchIdAndActiveTrue(integer);
        return new ApiResponse(mealScheduleForWeeks, true);
    }

    @Override
    public ApiResponse update(MealScheduleForWeekDto dto) {
        MealScheduleForWeek mealScheduleForWeek = mealScheduleForWeekRepository.findById(dto.getId()).orElseThrow(() -> new RecordNotFoundException(MEAL_SCHEDULE_NOT_FOUND));
        mealScheduleForWeek.setStartDay(dto.getStartDay());
        mealScheduleForWeek.setEndDay(dto.getEndDay());
        if (dto.getMealScheduleIdList() != null) {
            List<MealScheduleForDay> mealScheduleForDayList = new ArrayList<>();
            dto.getMealScheduleIdList().forEach(obj -> {
                mealScheduleForDayList.add(mealScheduleForDayRepository.findById(obj).orElseThrow(() -> new RecordNotFoundException(MEAL_NOT_FOUND)));
            });
            mealScheduleForWeek.setMealScheduleForDayList(mealScheduleForDayList);
        }
        mealScheduleForWeekRepository.save(mealScheduleForWeek);
        return new ApiResponse(SUCCESSFULLY, true);
    }

    @Override
    public ApiResponse delete(Integer integer) {
        MealScheduleForWeek mealSchedule = mealScheduleForWeekRepository.findById(integer).orElseThrow(() -> new RecordNotFoundException(MEAL_SCHEDULE_NOT_FOUND));
        mealSchedule.setActive(false);
        mealScheduleForWeekRepository.save(mealSchedule);
        return new ApiResponse(DELETED, true);
    }
}
