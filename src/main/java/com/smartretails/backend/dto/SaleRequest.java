package com.smartretails.backend.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaleRequest {

    @NotNull
    private Long cashierId;

    @Builder.Default
    private BigDecimal discountTotal = BigDecimal.ZERO;

    @NotNull
    private String paymentMode;

}
