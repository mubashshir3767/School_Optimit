package com.example.service;

import com.example.entity.Business;
import com.example.exception.RecordAlreadyExistException;
import com.example.exception.RecordNotFoundException;
import com.example.model.common.ApiResponse;
import com.example.model.response.BusinessResponseListForAdmin;
import com.example.repository.BusinessRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import static com.example.enums.Constants.*;

@RequiredArgsConstructor
@Service
public class BusinessService implements BaseService<Business, Integer> {

    private final BusinessRepository businessRepository;

    @Override
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse create(Business business) {
        if (businessRepository.existsByName(business.getName())) {
            throw new RecordAlreadyExistException(BUSINESS_NAME_ALREADY_EXIST);
        }
        Business from = Business.from(business);
        return new ApiResponse(businessRepository.save(from), true);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getById(Integer integer) {
        Business business = businessRepository.findById(integer)
                .orElseThrow(() -> new RecordNotFoundException(BUSINESS_NOT_FOUND));
        return new ApiResponse(business, true);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse update(Business newBusiness) {
        if (businessRepository.existsByName(newBusiness.getName())) {
            throw new RecordAlreadyExistException(BUSINESS_NAME_ALREADY_EXIST);
        }
        Business business = businessRepository.findById(newBusiness.getId())
                .orElseThrow(() -> new RecordNotFoundException(BUSINESS_NOT_FOUND));
        business.setName(newBusiness.getName());
        business.setAddress(newBusiness.getAddress());
        business.setDescription(newBusiness.getDescription());
        businessRepository.save(business);
        return new ApiResponse(SUCCESSFULLY, true);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse delete(Integer integer) {
        Business business = businessRepository.findById(integer)
                .orElseThrow(() -> new RecordNotFoundException(BUSINESS_NOT_FOUND));
        business.setDelete(true);
        businessRepository.save(business);
        return new ApiResponse(DELETED, true);
    }
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Business> all = businessRepository.findAllByDeleteFalse(pageable);
        return new ApiResponse(new BusinessResponseListForAdmin(
                all.getContent(), all.getTotalElements(), all.getTotalPages(), all.getNumber()), true);
    }
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse deActivate(Integer integer) {
        Business business = businessRepository.findById(integer)
                .orElseThrow(() -> new RecordNotFoundException(BUSINESS_NOT_FOUND));
        business.setActive(false);
        businessRepository.save(business);
        return new ApiResponse(DEACTIVATED, true);
    }
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse activate(Integer integer) {
        Business business = businessRepository.findById(integer)
                .orElseThrow(() -> new RecordNotFoundException(BUSINESS_NOT_FOUND));
        business.setActive(true);
        businessRepository.save(business);
        return new ApiResponse(ACTIVATED, true);
    }
}
