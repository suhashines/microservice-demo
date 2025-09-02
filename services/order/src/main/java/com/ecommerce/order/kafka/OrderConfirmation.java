package com.ecommerce.order.kafka;

import com.ecommerce.order.dto.CustomerResponseDto;
import com.ecommerce.order.dto.ProductResponseDto;
import com.ecommerce.order.entity.PaymentMethod;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        Integer orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponseDto customer,
        List<ProductResponseDto> products
) {
}
