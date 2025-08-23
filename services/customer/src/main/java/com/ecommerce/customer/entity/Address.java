package com.ecommerce.customer.entity;

import org.springframework.validation.annotation.Validated;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@Validated
public class Address {
    
    private String street ;
    private String houseNumber;
    private String zipCode ;
}
