package com.ecommerce.order.payment;


import com.ecommerce.order.dto.PaymentRequestDto;
import com.ecommerce.order.dto.PaymentResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "payment-service",
        url = "${application.config.payment-url}"
)
public interface PaymentClient {
    @PostMapping
    public PaymentResponseDto createPayment(@RequestBody PaymentRequestDto paymentRequestDto);
}
