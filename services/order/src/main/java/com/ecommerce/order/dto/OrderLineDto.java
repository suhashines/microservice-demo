package com.ecommerce.order.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OrderLineDto(

        Integer id,
        @NotNull
        @Positive
        double quantity,
        @NotNull
        Integer productId
) {
}
