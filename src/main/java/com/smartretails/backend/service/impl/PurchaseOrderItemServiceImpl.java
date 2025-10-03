package com.smartretails.backend.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartretails.backend.entity.Product;
import com.smartretails.backend.entity.PurchaseOrder;
import com.smartretails.backend.entity.PurchaseOrderItem;
import com.smartretails.backend.exception.ResourceNotFoundException;
import com.smartretails.backend.repository.ProductRepository;
import com.smartretails.backend.repository.PurchaseOrderItemRepository;
import com.smartretails.backend.repository.PurchaseOrderRepository;
import com.smartretails.backend.service.PurchaseOrderItemService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PurchaseOrderItemServiceImpl implements PurchaseOrderItemService {

    private final PurchaseOrderItemRepository purchaseOrderItemJpaRepository;
    private final PurchaseOrderRepository purchaseOrderJpaRepository;
    private final ProductRepository productJpaRepository;

    @Override
    @Transactional
    public PurchaseOrderItem createPurchaseOrderItems(PurchaseOrderItem item) {
        PurchaseOrder purchaseOrder = purchaseOrderJpaRepository.findById(item.getPurchaseOrder().getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Purchase order not found with id" + item.getPurchaseOrder().getId()));

        Product product = productJpaRepository.findById(item.getProduct().getId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Product not found with id" + item.getProduct().getId()));
        item.setProduct(product);
        item.setPurchaseOrder(purchaseOrder);
        item.setCostPrice(item.getCostPrice());
        item.setQuantity(item.getQuantity());
        item.setReceivedQuantity(item.getReceivedQuantity());
        return purchaseOrderItemJpaRepository.save(item);
    }

    @Override
    public List<PurchaseOrderItem> getItemsByPurchaseOrder(Long purchaseOrderById) {
        PurchaseOrder purchaseOrder = purchaseOrderJpaRepository.findById(purchaseOrderById)
                .orElseThrow(() -> new RuntimeException("Purchase order not found!"));
        return purchaseOrderItemJpaRepository.findByPurchaseOrder(purchaseOrder);
    }

}
