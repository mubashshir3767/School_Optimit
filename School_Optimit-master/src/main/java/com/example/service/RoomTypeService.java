package com.example.service;

import com.example.entity.Branch;
import com.example.entity.RoomType;
import com.example.exception.RecordAlreadyExistException;
import com.example.exception.RecordNotFoundException;
import com.example.model.common.ApiResponse;
import com.example.repository.BranchRepository;
import com.example.repository.RoomTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

import static com.example.enums.Constants.*;

@Service
@RequiredArgsConstructor
public class RoomTypeService implements BaseService<RoomType, Integer> {

    private final RoomTypeRepository roomTypeRepository;
    private final BranchRepository branchRepository;

    @Override
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse create(RoomType newRoomType) {
        if (roomTypeRepository.existsByBranchIdAndName(newRoomType.getComingBranchId(), newRoomType.getName())) {
            throw new RecordAlreadyExistException(ROOM_TYPE_ALREADY_EXIST);
        }
        Branch branch = branchRepository.findById(newRoomType.getComingBranchId())
                .orElseThrow(() -> new RecordNotFoundException(BRANCH_NOT_FOUND));
        RoomType roomType = RoomType.builder()
                .name(newRoomType.getName())
                .branch(branch)
                .active(true)
                .build();
        roomTypeRepository.save(roomType);
        return new ApiResponse(SUCCESSFULLY, true);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getById(Integer integer) {
        RoomType roomType = roomTypeRepository.findById(integer)
                .orElseThrow(() -> new RecordNotFoundException(ROOM_TYPE_NOT_FOUND));
        return new ApiResponse(roomType, true);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse update(RoomType update) {
        RoomType roomType = roomTypeRepository.findById(update.getId())
                .orElseThrow(() -> new RecordNotFoundException(ROOM_TYPE_NOT_FOUND));
        roomType.setName(update.getName());
        roomTypeRepository.save(roomType);
        return new ApiResponse(SUCCESSFULLY, true);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse delete(Integer integer) {
        RoomType roomType = roomTypeRepository.findById(integer)
                .orElseThrow(() -> new RecordNotFoundException(ROOM_TYPE_NOT_FOUND));
        roomType.setActive(false);
        roomTypeRepository.save(roomType);
        return new ApiResponse(DELETED, true);
    }
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getListRoomsByBranchId(Integer id) {
        List<RoomType> allByBranchId = roomTypeRepository.findAllByBranchIdAndActiveTrue(id);
        return new ApiResponse(allByBranchId, true);
    }
}
