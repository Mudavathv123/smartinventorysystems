package com.smartretails.backend.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smartretails.backend.entity.Sale;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query("select count(s), coalesce(sum(s.total + s.discountTotal),0), coalesce(sum(s.discountTotal),0), coalesce(sum(s.total),0), coalesce(sum(s.taxTotal),0) from Sale s where s.createdAt between :from and :to")
    Object[] aggregateBetween(@Param("from") LocalDateTime from, @Param("to") LocalDateTime to);
}
