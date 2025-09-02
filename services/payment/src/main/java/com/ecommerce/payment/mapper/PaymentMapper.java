package com.ecommerce.payment.mapper;

import com.ecommerce.payment.dto.PaymentCreateDto;
import com.ecommerce.payment.dto.PaymentResponseDto;
import com.ecommerce.payment.entity.Payment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PaymentMapper {
    public Payment toPayment(PaymentCreateDto dto){
        return Payment.builder()
                .paymentMethod(dto.paymentMethod())
                .amount(dto.amount())
                .orderId(dto.orderId())
                .build();
    }

    public PaymentResponseDto toPaymentResponseDto(Payment payment){
        return new PaymentResponseDto(
                payment.getId(),payment.getPaymentMethod(),payment.getAmount(),payment.getOrderId()
        );
    }

    public List<PaymentResponseDto> toPaymentResponseDtoList(List<Payment> payments){
        return payments.stream().map(this::toPaymentResponseDto).collect(Collectors.toList());
    }
}
