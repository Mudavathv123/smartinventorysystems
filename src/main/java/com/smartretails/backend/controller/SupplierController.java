package com.smartretails.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartretails.backend.config.ApiResponse;
import com.smartretails.backend.dto.SupplierRequest;
import com.smartretails.backend.dto.SupplierDto;
import com.smartretails.backend.entity.Supplier;
import com.smartretails.backend.mapper.DtoMapper;
import com.smartretails.backend.service.SupplierService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class SupplierController {

    private final SupplierService supplierRepository;

    @PostMapping("/suppliers")
    public ResponseEntity<ApiResponse<SupplierDto>> createSupplier(@Valid @RequestBody SupplierRequest req) {
        Supplier supplier = Supplier.builder()
                .name(req.getName())
                .email(req.getEmail())
                .phone(req.getPhone())
                .address(req.getAddress())
                .contactPerson(req.getContactPerson())
                .isActive(req.getIsActive())
                .build();
        return ResponseEntity.ok(ApiResponse.success("Supplier created",
                DtoMapper.toSupplierDto(supplierRepository.createSupplier(supplier))));
    }
}
