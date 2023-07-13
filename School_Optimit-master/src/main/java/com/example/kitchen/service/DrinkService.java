package com.example.kitchen.service;

import com.example.entity.Branch;
import com.example.exception.RecordAlreadyExistException;
import com.example.exception.RecordNotFoundException;
import com.example.kitchen.entity.Drink;
import com.example.kitchen.entity.Product;
import com.example.kitchen.model.DrinkRequestDto;
import com.example.kitchen.repository.DrinkRepository;
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
public class DrinkService implements BaseService<DrinkRequestDto, Integer> {

    private final DrinkRepository drinkRepository;
    private final BranchRepository branchRepository;
    private final ProductRepository productRepository;


    @Override
    public ApiResponse create(DrinkRequestDto dto) {
        if (drinkRepository.existsByName(dto.getName())) {
            throw new RecordAlreadyExistException(DRINK_ALREADY_EXIST);
        }
        Branch branch = branchRepository.findById(dto.getBranchId()).orElseThrow(() -> new RecordNotFoundException(BRANCH_NOT_FOUND));
        List<Product> productList = new ArrayList<>();
        dto.getProductIdList().forEach(obj -> {
            productList.add(productRepository.findById(obj).orElseThrow(() -> new RecordNotFoundException(PRODUCT_NOT_FOUND)));
        });
        Drink drink = Drink.builder()
                .name(dto.getName())
                .productList(productList)
                .branch(branch)
                .active(true)
                .build();
        drinkRepository.save(drink);
        return new ApiResponse(SUCCESSFULLY, true);
    }

    @Override
    public ApiResponse getById(Integer integer) {
        List<Drink> drinkList = drinkRepository.findAllByBranchIdAndActiveTrue(integer);
        return new ApiResponse(drinkList, true);
    }

    @Override
    public ApiResponse update(DrinkRequestDto dto) {
        if (drinkRepository.existsByName(dto.getName())) {
            throw new RecordAlreadyExistException(DRINK_ALREADY_EXIST);
        }
        Drink drink = drinkRepository.findById(dto.getId()).orElseThrow(() -> new RecordNotFoundException(DRINK_NOT_FOUND));
        drink.setName(dto.getName());
        List<Product> productList = new ArrayList<>();
        dto.getProductIdList().forEach(obj -> {
            productList.add(productRepository.findById(obj).orElseThrow(() -> new RecordNotFoundException(PRODUCT_NOT_FOUND)));
        });
        drink.setProductList(productList);
        drinkRepository.save(drink);
        return new ApiResponse(SUCCESSFULLY, true);
    }

    @Override
    public ApiResponse delete(Integer integer) {
        Drink drink = drinkRepository.findById(integer).orElseThrow(() -> new RecordNotFoundException(DRINK_NOT_FOUND));
        drink.setActive(false);
        drinkRepository.save(drink);
        return new ApiResponse(DELETED, true);
    }
}
