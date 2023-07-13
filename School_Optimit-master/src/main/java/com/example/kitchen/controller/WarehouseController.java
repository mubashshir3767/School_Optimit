package com.example.kitchen.controller;

import com.example.kitchen.model.ProductAndQuantityDto;
import com.example.kitchen.model.WarehouseDto;
import com.example.kitchen.service.WarehouseService;
import com.example.model.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/warehouse")
public class WarehouseController {

    private final WarehouseService warehouseService;

    @PostMapping("/create")
    public ApiResponse create(@RequestBody WarehouseDto warehouseDto) {
        return warehouseService.create(warehouseDto);
    }

    @GetMapping("/getById/{id}")
    public ApiResponse getById(@PathVariable Integer id) {
        return warehouseService.getById(id);
    }

    @PutMapping("/update")
    public ApiResponse update(@RequestBody WarehouseDto warehouseDto) {
        return warehouseService.update(warehouseDto);
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse delete(@PathVariable Integer id) {
        return warehouseService.delete(id);
    }

    @PostMapping("/addProductToWarehouse/{id}")
    public ApiResponse add(@RequestBody List<ProductAndQuantityDto> productAndQuantityDtos, @PathVariable Integer id) {
        return warehouseService.addProductToWarehouse(productAndQuantityDtos, id);
    }

    @PostMapping("/getProductFromWarehouse/{id}")
    public ApiResponse get(@RequestBody List<ProductAndQuantityDto> productAndQuantityDtos, @PathVariable Integer id) {
        return warehouseService.getProductFromWarehouse(productAndQuantityDtos, id);
    }


//    @GetMapping("/getByBranchId")
//    public ApiResponse getByBranchId(
//            @RequestParam(name = "page", defaultValue = "0") Integer page,
//            @RequestParam(name = "size", defaultValue = "10") Integer size,
//            @RequestParam(name = "id") Integer id) {
//        return warehouseService.getByBranchId(page, size, id);
//    }
}
