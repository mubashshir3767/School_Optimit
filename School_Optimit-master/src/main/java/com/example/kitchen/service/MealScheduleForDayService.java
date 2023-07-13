package com.example.kitchen.service;

import com.example.entity.Branch;
import com.example.exception.RecordNotFoundException;
import com.example.kitchen.entity.Drink;
import com.example.kitchen.entity.Meal;
import com.example.kitchen.entity.MealScheduleForDay;
import com.example.kitchen.model.DrinkResponse;
import com.example.kitchen.model.MealResponse;
import com.example.kitchen.model.MealScheduleForDayDto;
import com.example.kitchen.model.MealScheduleForDayResponse;
import com.example.kitchen.repository.DrinkRepository;
import com.example.kitchen.repository.MealRepository;
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
public class MealScheduleForDayService implements BaseService<MealScheduleForDayDto, Integer> {

    private final MealRepository mealRepository;
    private final DrinkRepository drinkRepository;
    private final BranchRepository branchRepository;
    private final MealScheduleForDayRepository mealScheduleForDayRepository;


    @Override
    public ApiResponse create(MealScheduleForDayDto dto) {
        Branch branch = branchRepository.findById(dto.getBranchId()).orElseThrow(() -> new RecordNotFoundException(BRANCH_NOT_FOUND));
        List<Meal> mealList = new ArrayList<>();
        List<Drink> drinkList = new ArrayList<>();
        dto.getMealIdList().forEach(obj -> {
            mealList.add(mealRepository.findById(obj).orElseThrow(() -> new RecordNotFoundException(MEAL_NOT_FOUND)));
        });

        dto.getDrinkIdList().forEach(obj -> {
            drinkList.add(drinkRepository.findById(obj).orElseThrow(() -> new RecordNotFoundException(DRINK_NOT_FOUND)));
        });
        MealScheduleForDay mealScheduleForDay = MealScheduleForDay.builder()
                .weekDay(dto.getWeekDay())
                .branch(branch)
                .mealList(mealList)
                .drinkList(drinkList)
                .active(true)
                .build();
        mealScheduleForDayRepository.save(mealScheduleForDay);
        return new ApiResponse(SUCCESSFULLY, true);
    }

    @Override
    public ApiResponse getById(Integer integer) {
        List<MealScheduleForDay> mealScheduleForDayList = mealScheduleForDayRepository.findAllByBranchIdAndActiveTrue(integer);
        List<MealScheduleForDayResponse> responseList = new ArrayList<>();
        mealScheduleForDayList.forEach(mealScheduleForDay ->
                {
                    List<MealResponse> mealResponses = new ArrayList<>();
                    mealScheduleForDay.getMealList().forEach(onj -> {
                        mealResponses.add(MealResponse.from(onj));
                    });
                    responseList.add(MealScheduleForDayResponse.from(mealScheduleForDay, mealResponses));

                    List<DrinkResponse> drinkResponses = new ArrayList<>();
                    mealScheduleForDay.getDrinkList().forEach(onj -> {
                        drinkResponses.add(DrinkResponse.from(onj));
                    });
                    responseList.add(MealScheduleForDayResponse.from(mealScheduleForDay, mealResponses));
                }
        );
        return new ApiResponse(responseList, true);
    }

    @Override
    public ApiResponse update(MealScheduleForDayDto dto) {
        MealScheduleForDay mealScheduleForDay = mealScheduleForDayRepository.findById(dto.getId()).orElseThrow(() -> new RecordNotFoundException(MEAL_SCHEDULE_NOT_FOUND));
        List<Meal> mealList = new ArrayList<>();
        dto.getMealIdList().forEach(obj -> {
            mealList.add(mealRepository.findById(obj).orElseThrow(() -> new RecordNotFoundException(MEAL_NOT_FOUND)));
        });
        mealScheduleForDay.setMealList(mealList);
        mealScheduleForDay.setWeekDay(dto.getWeekDay());
        mealScheduleForDayRepository.save(mealScheduleForDay);
        return new ApiResponse(SUCCESSFULLY, true);
    }

    @Override
    public ApiResponse delete(Integer integer) {
        MealScheduleForDay mealScheduleForDay = mealScheduleForDayRepository.findById(integer).orElseThrow(() -> new RecordNotFoundException(MEAL_SCHEDULE_NOT_FOUND));
        mealScheduleForDay.setActive(false);
        mealScheduleForDayRepository.save(mealScheduleForDay);
        return new ApiResponse(DELETED, true);
    }
}
