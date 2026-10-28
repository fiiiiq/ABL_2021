package com.taufiq.konsumer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerService.class);

    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void consumeMessage(String message) {
        LOGGER.info("=================================================");
        LOGGER.info("📥 [KONSUMER] Berhasil menerima pesan dari RabbitMQ!");
        LOGGER.info("📦 Data: {}", message);
        LOGGER.info("=================================================");
    }
}
