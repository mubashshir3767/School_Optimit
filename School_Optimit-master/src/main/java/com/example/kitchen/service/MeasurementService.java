package com.example.kitchen.service;

import com.example.entity.Branch;
import com.example.exception.RecordAlreadyExistException;
import com.example.exception.RecordNotFoundException;
import com.example.kitchen.entity.Measurement;
import com.example.kitchen.model.MeasurementDto;
import com.example.kitchen.repository.MeasurementRepository;
import com.example.model.common.ApiResponse;
import com.example.repository.BranchRepository;
import com.example.service.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

import static com.example.enums.Constants.*;

@Service
@RequiredArgsConstructor
public class MeasurementService implements BaseService<MeasurementDto, Integer> {

    private final MeasurementRepository measurementRepository;
    private final BranchRepository branchRepository;

    @Override
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse create(MeasurementDto dto) {
        if (measurementRepository.existsByNameAndBranchIdAndActiveTrue(dto.getName(), dto.getBranchId())) {
            throw new RecordAlreadyExistException(MEASUREMENT_ALREADY_EXIST);
        }
        Branch branch = branchRepository.findById(dto.getBranchId()).orElseThrow(() -> new RecordNotFoundException(BRANCH_NOT_FOUND));
        Measurement measurement = Measurement.builder()
                .name(dto.getName())
                .branch(branch)
                .active(true)
                .build();
        measurementRepository.save(measurement);
        return new ApiResponse(SUCCESSFULLY, true);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getById(Integer integer) {
        Measurement measurement = measurementRepository.findById(integer).orElseThrow(() -> new RecordNotFoundException(MEASUREMENT_NOT_FOUND));
        return new ApiResponse(measurement, true);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse update(MeasurementDto dto) {
        Measurement measurement = measurementRepository.findById(dto.getId()).orElseThrow(() -> new RecordNotFoundException(MEASUREMENT_NOT_FOUND));
        measurement.setName(dto.getName());
        measurementRepository.save(measurement);
        return new ApiResponse(SUCCESSFULLY, true);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse delete(Integer integer) {
        Measurement measurement = measurementRepository.findById(integer).orElseThrow(() -> new RecordNotFoundException(MEASUREMENT_NOT_FOUND));
        measurement.setActive(false);
        measurementRepository.save(measurement);
        return new ApiResponse(DELETED, true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getByBranchId(Integer integer) {
        List<Measurement> measurements = measurementRepository.findAllByBranchIdAndActiveTrue(integer);
        return new ApiResponse(measurements, true);
    }

}
