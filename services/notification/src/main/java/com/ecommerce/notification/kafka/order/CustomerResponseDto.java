package com.ecommerce.notification.kafka.order;

public record CustomerResponseDto(
        String id,
        String name,
        String email
) {
}
