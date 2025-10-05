package com.smartretails.backend.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.smartretails.backend.entity.StockBatch;
import com.smartretails.backend.repository.StockBatchRepository;
import com.smartretails.backend.service.InventoryService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final StockBatchRepository stockBatchRepository;

    @Override
    public List<StockBatch> geStockBatchs(Long productId) {
        return stockBatchRepository.findByProduct_Id(productId);
    }

    @Override
    @Transactional
    public StockBatch addBatch(StockBatch batch) {
        return stockBatchRepository.save(batch);
    }
}
