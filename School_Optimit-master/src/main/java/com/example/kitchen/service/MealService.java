package com.example.kitchen.service;

import com.example.entity.Branch;
import com.example.exception.RecordAlreadyExistException;
import com.example.exception.RecordNotFoundException;
import com.example.kitchen.entity.Meal;
import com.example.kitchen.entity.Product;
import com.example.kitchen.model.MealRequestDto;
import com.example.kitchen.repository.MealRepository;
import com.example.kitchen.repository.ProductRepository;
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
public class MealService implements BaseService<MealRequestDto, Integer> {

    private final MealRepository mealRepository;
    private final BranchRepository branchRepository;
    private final ProductRepository productRepository;


    @Override
    public ApiResponse create(MealRequestDto dto) {
        if (mealRepository.existsByName(dto.getName())) {
            throw new RecordAlreadyExistException(DRINK_ALREADY_EXIST);
        }
        Branch branch = branchRepository.findById(dto.getBranchId()).orElseThrow(() -> new RecordNotFoundException(BRANCH_NOT_FOUND));
        List<Product> productList = new ArrayList<>();
        dto.getProductIdList().forEach(obj -> {
            productList.add(productRepository.findById(obj).orElseThrow(() -> new RecordNotFoundException(PRODUCT_NOT_FOUND)));
        });
        Meal meal = Meal.builder()
                .name(dto.getName())
                .productList(productList)
                .branch(branch)
                .active(true)
                .build();
        mealRepository.save(meal);
        return new ApiResponse(SUCCESSFULLY, true);
    }

    @Override
    public ApiResponse getById(Integer integer) {
        List<Meal> mealList = mealRepository.findAllByBranchIdAndActiveTrue(integer);
        return new ApiResponse(mealList, true);
    }

    @Override
    public ApiResponse update(MealRequestDto dto) {
        if (mealRepository.existsByName(dto.getName())) {
            throw new RecordAlreadyExistException(DRINK_ALREADY_EXIST);
        }
        Meal meal = mealRepository.findById(dto.getId()).orElseThrow(() -> new RecordNotFoundException(MEAL_NOT_FOUND));
        meal.setName(dto.getName());
        List<Product> productList = new ArrayList<>();
        dto.getProductIdList().forEach(obj -> {
            productList.add(productRepository.findById(obj).orElseThrow(() -> new RecordNotFoundException(PRODUCT_NOT_FOUND)));
        });
        meal.setProductList(productList);
        mealRepository.save(meal);
        return new ApiResponse(SUCCESSFULLY, true);
    }

    @Override
    public ApiResponse delete(Integer integer) {
        Meal meal = mealRepository.findById(integer).orElseThrow(() -> new RecordNotFoundException(MEAL_NOT_FOUND));
        meal.setActive(false);
        mealRepository.save(meal);
        return new ApiResponse(DELETED, true);
    }
}
