package com.smartretails.backend.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartretails.backend.entity.Supplier;
import com.smartretails.backend.repository.SupplierRepository;
import com.smartretails.backend.service.SupplierService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierJpaRepository;

    @Override
    @Transactional
    public Supplier createSupplier(Supplier supplier) {
        return supplierJpaRepository.save(supplier);
    }

}
