package com.ecommerce.notification.service;

import com.ecommerce.notification.entity.EmailTemplates;
import com.ecommerce.notification.kafka.order.ProductResponseDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    private static final String FROM_EMAIL = "suhasabdullah43@gmail.com";

    @Async
    public void sendPaymentConfirmationEmail(
            String recipientEmail,
            String recipientName,
            Integer orderId,
            BigDecimal amount
    ) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("recipientName", recipientName);
        variables.put("orderId", orderId);
        variables.put("amount", amount);

        sendEmail(recipientEmail, EmailTemplates.PAYMENT_CONFIRMATION, variables);
    }

    public void sendOrderConfirmationEmail(
            String recipientEmail,
            String recipientName,
            Integer orderId,
            BigDecimal amount,
            List<ProductResponseDto> productResponseDtos
    ) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("recipientName", recipientName);
        variables.put("orderId", orderId);
        variables.put("amount", amount);
        variables.put("products", productResponseDtos);

        sendEmail(recipientEmail, EmailTemplates.ORDER_CONFIRMATION, variables);
    }

    private void sendEmail(
            String recipientEmail,
            EmailTemplates template,
            Map<String, Object> variables
    ) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(
                    mimeMessage,
                    MimeMessageHelper.MULTIPART_MODE_RELATED,
                    StandardCharsets.UTF_8.name()
            );

            helper.setFrom(FROM_EMAIL);
            helper.setTo(recipientEmail);
            helper.setSubject(template.getSubject());

            Context context = new Context();
            context.setVariables(variables);

            String htmlTemplate = templateEngine.process(template.getTemplate(), context);
            helper.setText(htmlTemplate, true);

            mailSender.send(mimeMessage);

            log.info("Email sent to {} with subject {}", recipientEmail, template.getSubject());
        } catch (MessagingException e) {
            log.warn("WARNING - Cannot send email with template {}", template.name(), e);
        }
    }
}
