package com.smartretails.backend.service;

import com.smartretails.backend.dto.SupplierDto;
import com.smartretails.backend.entity.Supplier;

public interface SupplierService {
    Supplier createSupplier(Supplier supplier);

    SupplierDto getSupplierById(Long id);

    SupplierDto updateSupplier(Long id, Supplier req);

    void deleteSupplier(Long id);
}
