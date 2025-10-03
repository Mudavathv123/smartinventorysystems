package com.smartretails.backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartretails.backend.config.ApiResponse;
import com.smartretails.backend.entity.PurchaseOrderItem;
import com.smartretails.backend.service.PurchaseOrderItemService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/purchase-order-items")
@RequiredArgsConstructor
public class PurchaseOrderItemController {

    private final PurchaseOrderItemService purchaseOrderItemRepository;

    @PostMapping
    public ResponseEntity<ApiResponse<PurchaseOrderItem>> createPurchaseOrderItem(
            @Valid @RequestBody PurchaseOrderItem purchaseOrderItem) {
        return ResponseEntity.ok(ApiResponse.success("Purchase order item saved..",
                purchaseOrderItemRepository.createPurchaseOrderItems(purchaseOrderItem)));
    }

    @GetMapping("/purchase-order/{purchaseOrderId}")
    public ResponseEntity<ApiResponse<List<PurchaseOrderItem>>> getItemsByPurchaseOrder(
            @PathVariable("purchaseOrderId") Long purchaseOrderId) {
        return ResponseEntity
                .ok(ApiResponse.success(purchaseOrderItemRepository.getItemsByPurchaseOrder(purchaseOrderId)));
    }

}
