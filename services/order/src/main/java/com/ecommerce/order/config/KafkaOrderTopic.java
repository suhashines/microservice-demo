package com.ecommerce.order.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaOrderTopic {
    @Bean
    public NewTopic orderTopic() {
        return TopicBuilder.name("order-topic").build();
        //if this topic doesn't exist, kafka admin will auto create the topic for us
    }
}
