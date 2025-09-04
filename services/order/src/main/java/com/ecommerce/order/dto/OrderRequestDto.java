package com.ecommerce.order.dto;

import com.ecommerce.order.entity.PaymentMethod;

import java.math.BigDecimal;
import java.util.List;

public record OrderRequestDto(
    String customerId,
    //do not provide this field in request
    BigDecimal totalPrice,
    PaymentMethod paymentMethod,
    List<OrderLineDto> products
) {
}
