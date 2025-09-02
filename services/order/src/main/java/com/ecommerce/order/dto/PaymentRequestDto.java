package com.ecommerce.order.dto;

import com.ecommerce.order.entity.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequestDto(
        PaymentMethod paymentMethod,
        BigDecimal amount,
        Integer orderId,
        CustomerResponseDto customer
) {
}
