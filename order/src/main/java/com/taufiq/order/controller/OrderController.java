package com.taufiq.order.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.taufiq.order.VO.ResponTemplate;
import com.taufiq.order.entity.Order;
import com.taufiq.order.service.OrderService;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    // GET semua order
    @GetMapping("/api/order")
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    // GET order berdasarkan ID
    @GetMapping("/api/order/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {

        Order order = orderService.getOrderById(id);

        if (order != null) {
            return ResponseEntity.ok(order);
        }

        return ResponseEntity.notFound().build();
    }

    // GET order beserta produk
    @GetMapping("/api/order/{id}/produk")
    public List<ResponTemplate> getOrderWithProduk(@PathVariable Long id) {
        return orderService.getOrderWithProdukById(id);
    }

    // POST order
    @PostMapping("/api/order")
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        return ResponseEntity.ok(orderService.saveOrder(order));
    }

    // PUT order
    @PutMapping("/api/order/{id}")
    public ResponseEntity<Order> updateOrder(
            @PathVariable Long id,
            @RequestBody Order order) {

        Order updatedOrder = orderService.updateOrder(id, order);

        if (updatedOrder != null) {
            return ResponseEntity.ok(updatedOrder);
        }

        return ResponseEntity.notFound().build();
    }

    // DELETE order
    @DeleteMapping("/api/order/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {

        Order order = orderService.getOrderById(id);

        if (order == null) {
            return ResponseEntity.notFound().build();
        }

        orderService.deleteOrder(id);

        return ResponseEntity.noContent().build();
    }
}