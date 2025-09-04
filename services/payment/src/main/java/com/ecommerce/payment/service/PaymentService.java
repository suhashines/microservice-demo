package com.ecommerce.payment.service;

import com.ecommerce.payment.dto.PaymentCreateDto;
import com.ecommerce.payment.dto.PaymentResponseDto;
import com.ecommerce.payment.entity.Payment;
import com.ecommerce.payment.kafka.PaymentConfirmation;
import com.ecommerce.payment.kafka.PaymentProducer;
import com.ecommerce.payment.mapper.PaymentMapper;
import com.ecommerce.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final PaymentProducer paymentProducer;

    public PaymentResponseDto createPayment(PaymentCreateDto paymentCreateDto) {
        Payment savedPayment = paymentRepository.save(paymentMapper.toPayment(paymentCreateDto));
        // todo inform notification service that payment has been received
        paymentProducer.sendPaymentConfirmation(
                new PaymentConfirmation(
                        savedPayment.getId(),
                        paymentCreateDto.orderId(),
                        paymentCreateDto.customer().name(),
                        paymentCreateDto.customer().email(),
                        paymentCreateDto.paymentMethod(),
                        paymentCreateDto.amount()
                )
        );

        return paymentMapper.toPaymentResponseDto(savedPayment);
    }

    public PaymentResponseDto getPaymentById(Integer id) {
        return paymentMapper.toPaymentResponseDto(Objects.requireNonNull(paymentRepository.findById(id).orElse(null)));
    }

    public List<PaymentResponseDto> getAllPayments() {
        return paymentMapper.toPaymentResponseDtoList(paymentRepository.findAll());
    }
}
