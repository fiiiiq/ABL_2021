package com.taufiq.order.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import com.taufiq.order.entity.Order;
import com.taufiq.order.repository.OrderRepository;
import com.taufiq.order.VO.Produk;
import com.taufiq.order.VO.Pelanggan;
import com.taufiq.order.VO.ResponTemplate;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private RestTemplate restTemplate;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    public Order updateOrder(Long id, Order orderBaru) {

        Order existingOrder = orderRepository.findById(id).orElse(null);

        if (existingOrder != null) {

            existingOrder.setIdproduk(orderBaru.getIdproduk());
            existingOrder.setIdpelanggan(orderBaru.getIdpelanggan());
            existingOrder.setTgl_order(orderBaru.getTgl_order());
            existingOrder.setJumlah(orderBaru.getJumlah());
            existingOrder.setTotal_harga(orderBaru.getTotal_harga());

            return orderRepository.save(existingOrder);
        }

        return null;
    }

    public List<ResponTemplate> getOrderWithProdukById(Long id) {

        List<ResponTemplate> responList = new ArrayList<>();

        Order order = getOrderById(id);

        if (order == null) {
            return responList;
        }

        ServiceInstance serviceInstance =
                discoveryClient.getInstances("PRODUK").get(0);

        Produk produk = restTemplate.getForObject(
                serviceInstance.getUri() + "/api/produk/"
                        + order.getIdproduk(),
                Produk.class
        );

        ServiceInstance pelangganServiceInstance =
            discoveryClient.getInstances("PELANGGAN").get(0);

        Pelanggan pelanggan = restTemplate.getForObject(
            pelangganServiceInstance.getUri() + "/api/pelanggan/"
                + order.getIdpelanggan(),
            Pelanggan.class
        );

        ResponTemplate VO = new ResponTemplate();

        VO.setOrder(order);
        VO.setProduk(produk);
        VO.setPelanggan(pelanggan);

        responList.add(VO);

        return responList;
    }
}