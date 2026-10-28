package com.zikra.order.service;

import com.zikra.order.entity.Order;
import com.zikra.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public List<Order> getAllOrder() { return orderRepository.findAll(); }
    public Order saveOrder(Order order) { return orderRepository.save(order); }
    public Order getOrderById(Long id) { return orderRepository.findById(id).orElse(null); }
}
