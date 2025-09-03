package com.ecommerce.notification.kafka;

import com.ecommerce.notification.entity.Notification;
import com.ecommerce.notification.entity.NotificationType;
import com.ecommerce.notification.kafka.order.OrderConfirmation;
import com.ecommerce.notification.kafka.payment.PaymentConfirmation;
import com.ecommerce.notification.repository.NotificationRepository;
import com.ecommerce.notification.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationConsumer {

    private final NotificationRepository notificationRepository;
    private final MailService mailService;

    @KafkaListener(topics = "order-topic")
    public void consumeOrderConfirmation(OrderConfirmation orderConfirmation) {
        log.info("Consuming Order confirmation: {}", orderConfirmation);

        Notification notification = Notification.builder()
                .notificationType(NotificationType.ORDER_CONFIRMATION)
                .notification(orderConfirmation)
                .build();
        notificationRepository.save(notification);

        // todo send mail to the customer

        mailService.sendOrderConfirmationEmail(
                orderConfirmation.customer().email(),orderConfirmation.customer().name(),orderConfirmation.orderReference(),orderConfirmation.totalAmount(),orderConfirmation.products()
        );
    }

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
        mailService.sendPaymentConfirmationEmail(
                paymentConfirmation.customerEmail(),paymentConfirmation.customerName(),paymentConfirmation.orderId(),paymentConfirmation.amount()
        );

    }
}
