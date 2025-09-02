package com.ecommerce.notification.kafka.payment;

import java.math.BigDecimal;

public record PaymentConfirmation (
        Integer paymentId,
        Integer orderId,
        String customerName,
        String customerEmail,
        PaymentMethod paymentMethod,
        BigDecimal amount
){}

