## Springboot
When a class is annotated with @Configuration, Spring treats it as a configuration class, similar to an XML configuration file.
Methods annotated with @Bean inside this class will be invoked, and their return values will be registered as beans in the Spring context.
The class can also use dependency injection and other Spring features.

Example:

```java

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
    }
}

```

## Hibernate

For creating tables automatically we use `ddl-auto` property in the application.properties file. The possible values are:
- `none`: No action will be performed.  
- 'validate': Hibernate will validate the schema, making sure it matches the entities. If there are discrepancies, an exception will be thrown.
- `update`: Hibernate will update the database schema to match the entities. This can include adding new tables, columns, and constraints, but it won't remove any existing tables or columns.