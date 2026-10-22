package com.taufiq.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {

    @RabbitListener(queues = "order-queue")
    public void processMessage(String message) {
        System.out.println("received: " + message);
    }
}