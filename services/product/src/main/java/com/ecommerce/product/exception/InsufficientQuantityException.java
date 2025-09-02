package com.ecommerce.product.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class InsufficientQuantityException extends RuntimeException {
    private final String msg;
}
