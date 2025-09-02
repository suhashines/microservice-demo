package com.ecommerce.notification.kafka.payment;

import com.ecommerce.notification.entity.Notification;
import com.ecommerce.notification.entity.NotificationType;
import com.ecommerce.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentConsumer {

    private final NotificationRepository notificationRepository;

    @KafkaListener(topics = "payment-topic")
    public void consumePaymentConfirmation(PaymentConfirmation paymentConfirmation) {

        log.info("Consuming Payment Confirmation: {}", paymentConfirmation);
        //save the notification
        Notification notification = Notification.builder()
                .notificationType(NotificationType.PAYMENT_CONFIRMATION)
                .notification(paymentConfirmation)
                .build();
        notificationRepository.save(notification);

        log.info("Sending mail to customer: {}",paymentConfirmation.customerEmail());
        // todo send mail to the customer
    }
}
