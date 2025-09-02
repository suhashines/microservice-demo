package com.ecommerce.payment.dto;

import org.springframework.validation.annotation.Validated;

@Validated
public record Customer(
        String name,
        String email
) {
}
