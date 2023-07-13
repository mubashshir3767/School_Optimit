package com.example.service;

import com.example.entity.Branch;
import com.example.entity.Room;
import com.example.entity.RoomType;
import com.example.exception.RecordNotFoundException;
import com.example.model.common.ApiResponse;
import com.example.model.request.RoomRequestDto;
import com.example.model.response.RoomResponseListForAdmin;
import com.example.repository.BranchRepository;
import com.example.repository.RoomRepository;
import com.example.repository.RoomTypeRepository;
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
public class RoomService implements BaseService<RoomRequestDto, Integer> {

    private final RoomRepository roomRepository;
    private final BranchRepository branchRepository;
    private final RoomTypeRepository roomTypeRepository;

    @Override
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse create(RoomRequestDto roomRequestDto) {
        if (roomRepository.existsByBranchIdAndRoomNumber(roomRequestDto.getBranchId(), roomRequestDto.getRoomNumber())) {
            throw new RecordNotFoundException(ROOM_NUMBER_ALREADY_EXIST);
        }
        Branch branch = branchRepository.findById(roomRequestDto.getBranchId())
                .orElseThrow(() -> new RecordNotFoundException(BRANCH_NOT_FOUND));
        RoomType roomType = roomTypeRepository.findById(roomRequestDto.getRoomTypeId())
                .orElseThrow(() -> new RecordNotFoundException(ROOM_TYPE_NOT_FOUND));
        Room room = Room.builder()
                .roomNumber(roomRequestDto.getRoomNumber())
                .branch(branch)
                .roomType(roomType)
                .active(true)
                .build();
        roomRepository.save(room);
        return new ApiResponse(SUCCESSFULLY, true);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getById(Integer integer) {
        Room room = roomRepository.findById(integer)
                .orElseThrow(() -> new RecordNotFoundException(ROOM_NOT_FOUND));
        return new ApiResponse(room, true);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse update(RoomRequestDto roomRequestDto) {
        if (roomRepository.existsByBranchIdAndRoomNumber(roomRequestDto.getBranchId(), roomRequestDto.getRoomNumber())) {
            throw new RecordNotFoundException(ROOM_NUMBER_ALREADY_EXIST);
        }
        Room room = roomRepository.findById(roomRequestDto.getRoomId())
                .orElseThrow(() -> new RecordNotFoundException(ROOM_NOT_FOUND));
        RoomType roomType = roomTypeRepository.findById(roomRequestDto.getRoomTypeId())
                .orElseThrow(() -> new RecordNotFoundException(ROOM_TYPE_NOT_FOUND));
        room.setRoomNumber(roomRequestDto.getRoomNumber());
        room.setRoomType(roomType);
        roomRepository.save(room);
        return new ApiResponse(SUCCESSFULLY, true);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse delete(Integer integer) {
        Room room = roomRepository.findById(integer)
                .orElseThrow(() -> new RecordNotFoundException(ROOM_NOT_FOUND));
        room.setActive(false);
        roomRepository.save(room);
        return new ApiResponse(DELETED, true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getRoomListByBranchId(int page, int size, Integer branchId) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Room> all = roomRepository.findAllByBranchIdAndActiveTrue(branchId, pageable);
        return new ApiResponse(new RoomResponseListForAdmin(
                all.getContent(), all.getTotalElements(), all.getTotalPages(), all.getNumber()), true);
    }
}
