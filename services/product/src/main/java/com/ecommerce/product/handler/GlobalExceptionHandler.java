package com.ecommerce.product.handler;

import com.ecommerce.product.exception.InsufficientQuantityException;
import com.ecommerce.product.exception.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<String> handleProductNotFoundException(ProductNotFoundException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMsg());
    }

    @ExceptionHandler(InsufficientQuantityException.class)
    public ResponseEntity<String> handleInsufficientQuantityException(InsufficientQuantityException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMsg());
    }
}
