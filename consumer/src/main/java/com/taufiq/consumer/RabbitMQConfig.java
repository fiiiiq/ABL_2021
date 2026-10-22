package com.taufiq.consumer;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue queue() {
        return new Queue("order-queue", true);
    }

    // ===== Notifikasi email dari service Order (CQRS) =====

    @Value("${app.rabbitmq.exchange}")
    private String exchangeName;

    @Value("${app.rabbitmq.email-queue}")
    private String emailQueueName;

    @Value("${app.rabbitmq.email-routing-key}")
    private String emailRoutingKey;

    @Bean
    public TopicExchange orderExchange() {
        return new TopicExchange(exchangeName, true, false);
    }

    @Bean
    public Queue orderEmailQueue() {
        return new Queue(emailQueueName, true);
    }

    @Bean
    public Binding orderEmailBinding() {
        return BindingBuilder.bind(orderEmailQueue()).to(orderExchange()).with(emailRoutingKey);
    }
}
