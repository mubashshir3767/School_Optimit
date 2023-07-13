package com.example.service;

import com.example.entity.TypeOfWork;
import com.example.enums.Constants;
import com.example.exception.RecordNotFoundException;
import com.example.model.common.ApiResponse;
import com.example.model.request.TypeOfWorkRequest;
import com.example.repository.TypeOfWorkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TypeOfWorkService implements BaseService<TypeOfWorkRequest, Integer> {

    private final TypeOfWorkRepository typeOfWorkRepository;

    @Override
    public ApiResponse create(TypeOfWorkRequest typeOfWorkRequest) {
        TypeOfWork typeOfWork = TypeOfWork.toTypeOfWork(typeOfWorkRequest);
        return new ApiResponse(typeOfWorkRepository.save(typeOfWork),true);
    }

    @Override
    public ApiResponse getById(Integer id) {
        return new ApiResponse(checkById(id),true);
    }

    public ApiResponse getAll(){
        return new ApiResponse(typeOfWorkRepository.findAll(),true);
    }

    @Override
    public ApiResponse update(TypeOfWorkRequest typeOfWorkRequest) {
        checkById(typeOfWorkRequest.getId());
        TypeOfWork typeOfWork = TypeOfWork.toTypeOfWork(typeOfWorkRequest);
        typeOfWork.setId(typeOfWorkRequest.getId());
        return new ApiResponse(typeOfWorkRepository.save(typeOfWork),true);
    }

    @Override
    public ApiResponse delete(Integer id) {
        TypeOfWork typeOfWork = checkById(id);
        typeOfWorkRepository.deleteById(id);
        return new ApiResponse(Constants.DELETED,true,typeOfWork);
    }

    public TypeOfWork checkById(Integer typeOfWorkRequest) {
        return typeOfWorkRepository.findById(typeOfWorkRequest).orElseThrow(() -> new RecordNotFoundException(Constants.TYPE_OF_WORK_NOT_FOUND));
    }
}
