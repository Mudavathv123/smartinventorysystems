package com.smartretails.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smartretails.backend.entity.Product;
import com.smartretails.backend.entity.StockBatch;

@Repository
public interface InventoryRepository extends JpaRepository<StockBatch, Long> {

    @Query("SELECT sb FROM StockBatch sb WHERE sb.product.id = :productId ORDER BY sb.expiryDate ASC NULLS LAST, sb.createdAt ASC")

    List<StockBatch> findBatchesByProduct(@Param("productId") Long productId);

    int countByProduct(Product product);
}
