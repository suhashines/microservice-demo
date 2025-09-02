package com.ecommerce.payment.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaPaymentTopic {
    @Bean
    public NewTopic paymentTopic() {
        return TopicBuilder.name("payment-topic").build();
    }
}
