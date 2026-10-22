package com.taufiq.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/produk")
public class ProducerController {
    @Autowired
    private ProducerService producerService;

    @PostMapping
    public String sendMessage(@RequestBody String message) {
        producerService.sendMessage(message);
        return "Message sent:" + message;
    }
}
