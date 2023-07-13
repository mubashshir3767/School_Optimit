package com.example.service;

import com.example.entity.Tariff;
import com.example.enums.Constants;
import com.example.enums.Lifetime;
import com.example.exception.RecordNotFoundException;
import com.example.model.common.ApiResponse;
import com.example.model.request.TariffDto;
import com.example.repository.PermissionRepository;
import com.example.repository.TariffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class TariffService implements BaseService<TariffDto, Integer> {

    private final TariffRepository repository;

    private final PermissionRepository permissionRepository;


    @Override
    public ApiResponse create(TariffDto tariffDto) {
        Tariff tariff = Tariff.toEntity(tariffDto);
        checkLifeTimeValid(tariffDto, tariff);
        setPermission(tariffDto, tariff);
        return new ApiResponse(Constants.SUCCESSFULLY, true, repository.save(tariff));
    }

    @Override
    public ApiResponse getById(Integer id) {
        return new ApiResponse(Constants.SUCCESSFULLY, true, checkById(id));
    }

    @Override
    public ApiResponse update(TariffDto tariffDto) {
        checkById(tariffDto.getId());
        Tariff entity = Tariff.toEntity(tariffDto);
        entity.setId(tariffDto.getId());
        checkLifeTimeValid(tariffDto,entity);
        setPermission(tariffDto,entity);
        return new ApiResponse(Constants.SUCCESSFULLY, true, repository.save(entity));
    }

    @Override
    public ApiResponse delete(Integer id) {
        Tariff tariff = checkById(id);
        tariff.setDelete(true);
        return new ApiResponse(Constants.DELETED, true,repository.save(tariff));
    }

    public ApiResponse getTariffList() {
        List<Tariff> tariffList = repository.findAllByDelete(false);
        tariffList.sort(Comparator.comparing(Tariff::getPrice));
        return new ApiResponse(Constants.FOUND, true, tariffList);
    }


    public ApiResponse getToChooseATariff() {
        List<Tariff> tariffList = repository.findAllByActiveAndDelete(true, false);
        tariffList.sort(Comparator.comparing(Tariff::getPrice));
        return new ApiResponse(Constants.FOUND, true, tariffList);
    }

    private Tariff checkById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new RecordNotFoundException(Constants.TARIFF_NOT_FOUND));
    }


    private void setPermission(TariffDto tariffDto, Tariff tariff) {
        tariff.setPermissions(permissionRepository.findAllById(tariffDto.getPermissionsList()));
    }

    private static void checkLifeTimeValid(TariffDto tariffDto, Tariff tariff) {
        try {
            tariff.setLifetime(Lifetime.valueOf(tariffDto.getLifetime()));
        } catch (Exception e) {
            throw new RecordNotFoundException(Constants.LIFE_TIME_DONT_MATCH + "    " + e);
        }
    }
}
