package com.ecommerce.order.dto;

import com.ecommerce.order.entity.PaymentMethod;

import java.math.BigDecimal;
import java.util.List;

public record OrderResponseDto(

        Integer id,
        String customerId,
        BigDecimal totalPrice,
        PaymentMethod paymentMethod,
        List<OrderLineDto> products
) {
}
