package com.ecommerce.notification.kafka.order;

import java.math.BigDecimal;

public record ProductResponseDto(
        Integer id,
        String name,
        BigDecimal price,
        Double quantity
) {
}
