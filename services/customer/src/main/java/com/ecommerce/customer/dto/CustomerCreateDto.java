package com.ecommerce.customer.dto;

import com.ecommerce.customer.entity.Address;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;


public record CustomerCreateDto(
    @NotBlank(message = "name is required")
     String name,
     @NotBlank(message = "Email is required")
     @Email(message="Email is not valid")
     String email,
     Address address
    
) {
    
}
