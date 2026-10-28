package com.taufiq.produser.controller;

import com.taufiq.produser.service.ProducerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class ProducerController {

    private final ProducerService producerService;

    public ProducerController(ProducerService producerService) {
        this.producerService = producerService;
    }

    // Endpoint GET untuk memudahkan testing langsung di browser: http://localhost:8084/api/v1/publish?message=HaloRabbitMQ
    @GetMapping("/publish")
    public ResponseEntity<Map<String, Object>> sendMessageGet(@RequestParam("message") String message) {
        producerService.sendMessage(message);
        
        Map<String, Object> response = new HashMap<>();
        response.put("status", "SUCCESS");
        response.put("message", "Pesan berhasil dikirim ke RabbitMQ!");
        response.put("payload", message);
        return ResponseEntity.ok(response);
    }

    // Endpoint POST untuk testing kirim body json/text via Postman atau frontend
    @PostMapping("/publish")
    public ResponseEntity<Map<String, Object>> sendMessagePost(@RequestBody Map<String, Object> body) {
        String message = body.toString();
        producerService.sendMessage(message);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "SUCCESS");
        response.put("message", "Pesan POST berhasil dikirim ke RabbitMQ!");
        response.put("payload", body);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/status")
    public ResponseEntity<Map<String, String>> getStatus() {
        Map<String, String> status = new HashMap<>();
        status.put("service", "produser");
        status.put("status", "running");
        return ResponseEntity.ok(status);
    }
}
