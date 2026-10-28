package com.taufiq.produser.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ProducerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProducerService.class);

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    private final RabbitTemplate rabbitTemplate;

    public ProducerService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    private int counter = 1;

    public void sendMessage(String message) {
        LOGGER.info("=================================================");
        LOGGER.info("📤 [PRODUSER] Mengirim pesan ke RabbitMQ -> {}", message);
        LOGGER.info("=================================================");
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }

    // Mengirim data secara otomatis setiap 10 detik
    @org.springframework.scheduling.annotation.Scheduled(fixedRate = 10000)
    public void sendScheduledMessage() {
        String autoMessage = "Data Produk #" + counter + " (Dikirim otomatis pada " + java.time.LocalDateTime.now() + ")";
        sendMessage(autoMessage);
        counter++;
    }
}
