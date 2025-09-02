package com.ecommerce.payment.dto;

import com.ecommerce.payment.entity.PaymentMethod;

import java.math.BigDecimal;

public record PaymentCreateDto(
        PaymentMethod paymentMethod,
        BigDecimal amount,
        Integer orderId,
        Customer customer
) {
}
