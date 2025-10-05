package com.smartretails.backend.service.impl;

import org.springframework.stereotype.Service;

import com.smartretails.backend.entity.Supplier;
import com.smartretails.backend.repository.SupplierRepository;
import com.smartretails.backend.service.SupplierService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;

    @Override
    @Transactional
    public Supplier createSupplier(Supplier supplier) {
        return supplierRepository.save(supplier);
    }
}
