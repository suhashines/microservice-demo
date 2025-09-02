package com.ecommerce.payment.dto;

import com.ecommerce.payment.entity.PaymentMethod;

import java.math.BigDecimal;

public record PaymentResponseDto(
        Integer id,
        PaymentMethod paymentMethod,
        BigDecimal amount,
        Integer orderId
) {
}
