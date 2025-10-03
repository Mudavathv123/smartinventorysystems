package com.smartretails.backend.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.smartretails.backend.entity.StockBatch;
import com.smartretails.backend.repository.InventoryRepository;
import com.smartretails.backend.service.InventoryService;

import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService{


    private final InventoryRepository inventoryJpaRepository;

    @Override
    public List<StockBatch> geStockBatchs(Long productId) {
        return inventoryJpaRepository.findBatchesByProduct(productId);
    }

    @Override
    @Transactional
    public StockBatch addBatch(StockBatch batch) {
        if(batch.getQuantity()< 0) {
            throw new ValidationException("Quantity cannot be negative..");
        }
        return inventoryJpaRepository.save(batch);
    }

}
