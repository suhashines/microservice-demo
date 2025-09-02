package com.ecommerce.order.dto;

import java.math.BigDecimal;

public record ProductResponseDto(
        Integer id,
        String name,
        BigDecimal price
) {
}
