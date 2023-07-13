package com.example.controller;

import com.example.model.common.ApiResponse;
import com.example.repository.PaymentTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/paymentType")
public class PaymentTypeController {

    private final PaymentTypeRepository paymentTypeRepository;

    @GetMapping("/getAll")
    public ApiResponse getAll() {
        return new ApiResponse(paymentTypeRepository.findAll(), true);
    }
}
