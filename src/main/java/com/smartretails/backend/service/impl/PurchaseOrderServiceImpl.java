package com.smartretails.backend.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartretails.backend.entity.PurchaseOrder;
import com.smartretails.backend.exception.ResourceNotFoundException;
import com.smartretails.backend.repository.PurchaseOrderRepository;
import com.smartretails.backend.service.PurchaseOrderService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    private final PurchaseOrderRepository purchaseOrderJpaRepository;

    @Override
    @Transactional
    public PurchaseOrder createPurchaseOrder(PurchaseOrder purchaseOrder) {
        return purchaseOrderJpaRepository.save(purchaseOrder);
    }

    @Override
    public List<PurchaseOrder> getAllOrders() {
        return purchaseOrderJpaRepository.findAll();
    }

    @Override
    public PurchaseOrder getOrderById(Long id) {
        return purchaseOrderJpaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Purchase order not found with id" + id));
    }

    @Override
    @Transactional
    public PurchaseOrder updateOrder(Long id, PurchaseOrder purchaseOrder) {
        PurchaseOrder existingOrder = getOrderById(id);

        existingOrder.setOrderNumber(purchaseOrder.getOrderNumber());
        existingOrder.setNotes(purchaseOrder.getNotes());
        existingOrder.setStatus(purchaseOrder.getStatus());
        existingOrder.setExpectedDate(purchaseOrder.getExpectedDate());
        existingOrder.setCreatedAt(purchaseOrder.getCreatedAt());
        existingOrder.setSupplier(purchaseOrder.getSupplier());
        existingOrder.setPurchaseOrderItems(purchaseOrder.getPurchaseOrderItems());
        existingOrder.setUpdatedAt(purchaseOrder.getUpdatedAt());

        return purchaseOrderJpaRepository.save(existingOrder);
    }

    @Override
    public void deleteOrder(Long id) {
        getOrderById(id);
        purchaseOrderJpaRepository.deleteById(id);
    }

}
