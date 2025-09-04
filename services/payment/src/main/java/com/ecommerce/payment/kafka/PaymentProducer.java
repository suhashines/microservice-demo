package com.ecommerce.payment.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class PaymentProducer {
    private final KafkaTemplate<String, PaymentConfirmation> kafkaTemplate;

    public void sendPaymentConfirmation (PaymentConfirmation paymentConfirmationTopic) {
        log.info("Sending payment confirmation: {}", paymentConfirmationTopic);
        Message<PaymentConfirmation> message = MessageBuilder.withPayload(paymentConfirmationTopic).setHeader(KafkaHeaders.TOPIC,"payment-topic").build();
        kafkaTemplate.send(message);
        log.info("Payment confirmation sent: {}", paymentConfirmationTopic);
    }
}
