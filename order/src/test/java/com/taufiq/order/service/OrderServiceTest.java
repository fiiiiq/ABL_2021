package com.taufiq.order.service;

import java.net.URI;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import com.taufiq.order.VO.Pelanggan;
import com.taufiq.order.VO.Produk;
import com.taufiq.order.VO.ResponTemplate;
import com.taufiq.order.entity.Order;
import com.taufiq.order.repository.OrderRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OrderServiceTest {

    @Test
    void returnsOrderWithProdukAndPelanggan() {
        OrderRepository orderRepository = mock(OrderRepository.class);
        DiscoveryClient discoveryClient = mock(DiscoveryClient.class);
        RestTemplate restTemplate = mock(RestTemplate.class);
        ServiceInstance produkInstance = mock(ServiceInstance.class);
        ServiceInstance pelangganInstance = mock(ServiceInstance.class);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(
                new Order(1L, 2L, 3L, Date.valueOf("2026-10-01"), 1, 100.0)));
        when(discoveryClient.getInstances("PRODUK")).thenReturn(List.of(produkInstance));
        when(discoveryClient.getInstances("PELANGGAN")).thenReturn(List.of(pelangganInstance));
        when(produkInstance.getUri()).thenReturn(URI.create("http://produk"));
        when(pelangganInstance.getUri()).thenReturn(URI.create("http://pelanggan"));

        Produk produk = new Produk(2L, "Buku", "Catatan", 100.0, 4L);
        Pelanggan pelanggan = new Pelanggan(3L, "Ayu", "Bandung", "P");
        when(restTemplate.getForObject("http://produk/api/produk/2", Produk.class))
                .thenReturn(produk);
        when(restTemplate.getForObject("http://pelanggan/api/pelanggan/3", Pelanggan.class))
                .thenReturn(pelanggan);

        OrderService orderService = new OrderService();
        ReflectionTestUtils.setField(orderService, "orderRepository", orderRepository);
        ReflectionTestUtils.setField(orderService, "discoveryClient", discoveryClient);
        ReflectionTestUtils.setField(orderService, "restTemplate", restTemplate);

        List<ResponTemplate> response = orderService.getOrderWithProdukById(1L);

        assertEquals(1, response.size());
        assertEquals("Buku", response.get(0).getProduk().getNama());
        assertEquals("Ayu", response.get(0).getPelanggan().getNama_pelanggan());
    }
}