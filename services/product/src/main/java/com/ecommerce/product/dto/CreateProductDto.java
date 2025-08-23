package com.ecommerce.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateProductDto(
         @NotBlank(message = "name is required")
         String name,
         String description,
         @NotNull
         BigDecimal price,
         @NotNull
         Double quantity,
         @NotNull
         Integer categoryId

) {
}
