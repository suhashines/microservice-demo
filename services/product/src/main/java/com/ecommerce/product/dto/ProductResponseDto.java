package com.ecommerce.product.dto;

import com.ecommerce.product.entity.Category;
import com.ecommerce.product.entity.Product;

import java.math.BigDecimal;

public record ProductResponseDto(
        Integer id,
        String name,
        String description,
        BigDecimal price,
        Double quantity,
        Integer categoryId
) {
}
