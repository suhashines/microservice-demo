package com.ecommerce.order.handler;

import com.ecommerce.order.exception.BusinessException;
import com.ecommerce.order.exception.OrderNotFoundException;
import feign.FeignException;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<String> handleFeignException(FeignException ex) {
        // You can inspect the status code from ex.status()
        if (ex.status() == 404) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ex.contentUTF8());
        }
        if (ex.status() == 400) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ex.contentUTF8());
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Something went wrong in downstream service");
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<String> handleBusinessException(BusinessException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMsg());
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<String> handleOrderNotFoundException(OrderNotFoundException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMsg());
    }
}
