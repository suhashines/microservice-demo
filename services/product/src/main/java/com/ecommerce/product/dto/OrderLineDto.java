package com.ecommerce.product.dto;

public record OrderLineDto(
        Integer productId,
        Double quantity
) {
}
