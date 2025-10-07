package com.smartretails.backend.service.impl;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.smartretails.backend.config.PageResponse;
import com.smartretails.backend.dto.SaleDto;
import com.smartretails.backend.dto.SaleRequest;
import com.smartretails.backend.entity.Sale;
import com.smartretails.backend.mapper.DtoMapper;
import com.smartretails.backend.repository.SaleRepository;
import com.smartretails.backend.service.SaleService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SeleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;

    @Override
    @Transactional
    public Sale createSale(SaleRequest saleRequest) {

        BigDecimal total = BigDecimal.ZERO;
        BigDecimal taxTotal = BigDecimal.ZERO;
        BigDecimal discountTotal = saleRequest.getDiscountTotal() != null ? saleRequest.getDiscountTotal()
                : BigDecimal.ZERO;

        Sale sale = Sale.builder()
                .cashierId(saleRequest.getCashierId())
                .discountTotal(discountTotal)
                .total(total)
                .taxTotal(taxTotal)
                .paymentMode(Sale.PaymentMode.valueOf(saleRequest.getPaymentMode()))
                .build();
        return saleRepository.save(sale);
    }

    @Override
    public Sale getSaleById(Long id) {

        return saleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sale not found with id: " + id));

    }

    @Override
    public PageResponse<SaleDto> getAllSales(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Sale> salePage = saleRepository.findAll(pageable);

        return PageResponse.<SaleDto>builder()
                .content(salePage.getContent().stream().map(DtoMapper::toSaleDto).toList())
                .page(salePage.getNumber())
                .size(salePage.getSize())
                .totalElements(salePage.getTotalElements())
                .totalPages(salePage.getTotalPages())
                .first(salePage.isFirst())
                .last(salePage.isLast())
                .empty(salePage.isEmpty())
                .build();
    }

}
