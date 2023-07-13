package com.example.kitchen.service;

import com.example.entity.Branch;
import com.example.exception.RecordAlreadyExistException;
import com.example.exception.RecordNotFoundException;
import com.example.kitchen.entity.Measurement;
import com.example.kitchen.entity.Product;
import com.example.kitchen.model.ProductDto;
import com.example.kitchen.model.ProductResponseList;
import com.example.kitchen.repository.MeasurementRepository;
import com.example.kitchen.repository.ProductRepository;
import com.example.model.common.ApiResponse;
import com.example.repository.BranchRepository;
import com.example.service.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import static com.example.enums.Constants.*;

@Service
@RequiredArgsConstructor
public class ProductService implements BaseService<ProductDto, Integer> {

    private final ProductRepository productRepository;
    private final MeasurementRepository measurementRepository;
    private final BranchRepository branchRepository;

    @Override
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse create(ProductDto dto) {
        if (productRepository.existsByBranchIdAndMeasurementIdAndNameAndActiveTrue(dto.getBranchId(), dto.getMeasurementId(), dto.getName())) {
            throw new RecordAlreadyExistException(PRODUCT_ALREADY_EXIST);
        }
        Branch branch = branchRepository.findById(dto.getBranchId()).orElseThrow(() -> new RecordNotFoundException(BRANCH_NOT_FOUND));
        Measurement measurement = measurementRepository.findById(dto.getMeasurementId()).orElseThrow(() -> new RecordNotFoundException(MEASUREMENT_NOT_FOUND));
        Product product = Product.builder()
                .branch(branch)
                .measurement(measurement)
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .active(true)
                .build();
        productRepository.save(product);
        return new ApiResponse(SUCCESSFULLY, true);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getById(Integer integer) {
        Product product = productRepository.findById(integer).orElseThrow(() -> new RecordNotFoundException(PRODUCT_NOT_FOUND));
        return new ApiResponse(product, true);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse update(ProductDto dto) {
        Product product = productRepository.findById(dto.getId()).orElseThrow(() -> new RecordNotFoundException(PRODUCT_NOT_FOUND));
        Measurement measurement = measurementRepository.findById(dto.getMeasurementId()).orElseThrow(() -> new RecordNotFoundException(MEASUREMENT_NOT_FOUND));
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setMeasurement(measurement);
        product.setDescription(dto.getDescription());
        productRepository.save(product);
        return new ApiResponse(SUCCESSFULLY, true);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse delete(Integer integer) {
        Product product = productRepository.findById(integer).orElseThrow(() -> new RecordNotFoundException(PRODUCT_NOT_FOUND));
        product.setActive(false);
        productRepository.save(product);
        return new ApiResponse(DELETED, true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getByBranchId(Integer page, Integer size, Integer integer) {
        Pageable page1 = PageRequest.of(page, size);
        Page<Product> product = productRepository.findAllByBranchIdAndActiveTrue(integer, page1);
        ProductResponseList productResponseList = ProductResponseList.builder()
                .products(product.getContent())
                .totalElement(product.getTotalElements())
                .totalPage(product.getTotalPages())
                .size(product.getSize())
                .build();
        return new ApiResponse(productResponseList, true);
    }
}
