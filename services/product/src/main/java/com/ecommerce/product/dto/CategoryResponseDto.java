package com.ecommerce.product.dto;

import java.util.List;

public record CategoryResponseDto(
        Integer id,
        String name,
        String description,
        List<ProductResponseDto> products
) {
}
