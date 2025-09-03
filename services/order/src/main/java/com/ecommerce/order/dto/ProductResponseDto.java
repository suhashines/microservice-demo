package com.ecommerce.order.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;


@Data
@Builder
public class ProductResponseDto{
    Integer id ;
    String name;
    BigDecimal price;
    Double quantity;
}


