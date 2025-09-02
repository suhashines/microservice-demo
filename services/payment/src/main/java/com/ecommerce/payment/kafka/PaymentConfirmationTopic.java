package com.ecommerce.payment.kafka;

import com.ecommerce.payment.dto.Customer;
import com.ecommerce.payment.entity.PaymentMethod;

import java.math.BigDecimal;

public record PaymentConfirmationTopic(
        Integer paymentId,
        Integer orderId,
        String customerName,
        String customerEmail,
        PaymentMethod paymentMethod,
        BigDecimal amount
) {
}
