package com.ecommerce.payment.kafka;

import com.ecommerce.payment.entity.PaymentMethod;

import java.math.BigDecimal;

public record PaymentConfirmation(
        Integer paymentId,
        Integer orderId,
        String customerName,
        String customerEmail,
        PaymentMethod paymentMethod,
        BigDecimal amount
) {
}
