package com.smartretails.backend.service;

import java.util.List;

import com.smartretails.backend.entity.PurchaseOrder;

public interface PurchaseOrderService {

    PurchaseOrder createPurchaseOrder(PurchaseOrder purchaseOrder);

    List<PurchaseOrder> getAllOrders();

    PurchaseOrder getOrderById(Long id);

    PurchaseOrder updateOrder(Long id, PurchaseOrder purchaseOrder);

    void deleteOrder(Long id);
}
