package com.zikra.order.controller;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.zikra.order.dto.OrderDetailResponse;
import com.zikra.order.dto.OrderEvent;
import com.zikra.order.entity.Order;
import com.zikra.order.service.OrderService;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.order}")
    private String exchange;

    @Value("${rabbitmq.routing.order}")
    private String routingKey;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    // GET semua order (biasa)
    @GetMapping
    public List<Order> getAllOrder() { return orderService.getAllOrder(); }

    // GET order by ID - luaran biasa
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        return order != null ? ResponseEntity.ok(order) : ResponseEntity.notFound().build();
    }

    // GET order by ID - LUARAN 2: tampilkan detail produk & pelanggan
    @GetMapping("/{id}/detail")
    public ResponseEntity<OrderDetailResponse> getOrderDetail(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        if (order == null) {
            return ResponseEntity.notFound().build();
        }

        OrderDetailResponse response = new OrderDetailResponse();
        response.setId(order.getId());
        response.setTglTransaksi(order.getTglTransaksi());
        response.setJumlah(order.getJumlah());
        response.setTotal(order.getTotal());

        // Panggil service PRODUK
        try {
            List<ServiceInstance> instances = discoveryClient.getInstances("produk");
            if (instances != null && !instances.isEmpty()) {
                ServiceInstance instance = instances.get(0);
                OrderDetailResponse.ProdukDTO produk = restTemplate.getForObject(
                    instance.getUri() + "/api/produk/" + order.getIdProduk(),
                    OrderDetailResponse.ProdukDTO.class
                );
                response.setProduk(produk);
            }
        } catch (Exception e) {
            LOGGER.warn("Gagal mengambil detail produk {}: {}", order.getIdProduk(), e.getMessage());
        }

        // Panggil service PELANGGAN
        try {
            List<ServiceInstance> instances = discoveryClient.getInstances("pelanggan");
            if (instances != null && !instances.isEmpty()) {
                ServiceInstance instance = instances.get(0);
                OrderDetailResponse.PelangganDTO pelanggan = restTemplate.getForObject(
                    instance.getUri() + "/api/pelanggan/" + order.getIdPelanggan(),
                    OrderDetailResponse.PelangganDTO.class
                );
                response.setPelanggan(pelanggan);
            }
        } catch (Exception e) {
            LOGGER.warn("Gagal mengambil detail pelanggan {}: {}", order.getIdPelanggan(), e.getMessage());
        }

        return ResponseEntity.ok(response);
    }

    // POST tambah order (Simpan ke DB lalu kirim event ke RabbitMQ untuk Email Service)
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        if (order.getTglTransaksi() == null) {
            order.setTglTransaksi(LocalDate.now());
        }

        // 1. Lengkapi data Produk & Pelanggan (dari input body, microservice, atau auto-calculate)
        String namaProduk = order.getNamaProduk();
        Double hargaProduk = order.getHargaProduk();

        if (namaProduk == null || namaProduk.trim().isEmpty() || hargaProduk == null || hargaProduk <= 0) {
            try {
                List<ServiceInstance> instances = discoveryClient.getInstances("produk");
                if (instances != null && !instances.isEmpty()) {
                    ServiceInstance instance = instances.get(0);
                    OrderDetailResponse.ProdukDTO produk = restTemplate.getForObject(
                        instance.getUri() + "/api/produk/" + order.getIdProduk(),
                        OrderDetailResponse.ProdukDTO.class
                    );
                    if (produk != null) {
                        if ((namaProduk == null || namaProduk.trim().isEmpty()) && produk.getNama() != null) {
                            namaProduk = produk.getNama();
                        }
                        if ((hargaProduk == null || hargaProduk <= 0) && produk.getHarga() != null) {
                            hargaProduk = produk.getHarga();
                        }
                    }
                }
            } catch (Exception e) {
                LOGGER.warn("Service produk tidak terjangkau: {}", e.getMessage());
            }
        }

        // Fallback cerdas jika nama produk belum ada
        if (namaProduk == null || namaProduk.trim().isEmpty()) {
            if (Long.valueOf(1).equals(order.getIdProduk())) {
                namaProduk = "Mouse Gaming RGB";
            } else if (Long.valueOf(2).equals(order.getIdProduk())) {
                namaProduk = "Keyboard Mechanical";
            } else {
                namaProduk = "Produk #" + order.getIdProduk();
            }
        }

        // Otomatis hitung harga satuan dari total / jumlah jika belum ada
        if (hargaProduk == null || hargaProduk <= 0) {
            if (order.getTotal() > 0 && order.getJumlah() > 0) {
                hargaProduk = order.getTotal() / order.getJumlah();
            } else {
                hargaProduk = 75000.0;
            }
        }

        String namaPelanggan = order.getNamaPelanggan();
        if (namaPelanggan == null || namaPelanggan.trim().isEmpty()) {
            try {
                List<ServiceInstance> instances = discoveryClient.getInstances("pelanggan");
                if (instances != null && !instances.isEmpty()) {
                    ServiceInstance instance = instances.get(0);
                    OrderDetailResponse.PelangganDTO pelanggan = restTemplate.getForObject(
                        instance.getUri() + "/api/pelanggan/" + order.getIdPelanggan(),
                        OrderDetailResponse.PelangganDTO.class
                    );
                    if (pelanggan != null && pelanggan.getNama() != null) {
                        namaPelanggan = pelanggan.getNama();
                    }
                }
            } catch (Exception e) {
                LOGGER.warn("Service pelanggan tidak terjangkau: {}", e.getMessage());
            }
        }

        if (namaPelanggan == null || namaPelanggan.trim().isEmpty()) {
            namaPelanggan = "Rataufiq Al Fitra";
        }

        // Set ke objek order sebelum disimpan
        order.setNamaProduk(namaProduk);
        order.setHargaProduk(hargaProduk);
        order.setNamaPelanggan(namaPelanggan);

        // 2. Simpan order lengkap ke Database
        Order savedOrder = orderService.saveOrder(order);
        LOGGER.info("💾 Order berhasil disimpan ke DB dengan ID: {}", savedOrder.getId());

        // 3. Bangun payload OrderEvent untuk RabbitMQ
        OrderEvent event = new OrderEvent(
            savedOrder.getId(),
            savedOrder.getIdProduk(),
            savedOrder.getNamaProduk(),
            savedOrder.getHargaProduk(),
            savedOrder.getIdPelanggan(),
            savedOrder.getNamaPelanggan(),
            savedOrder.getEmail(),
            savedOrder.getJumlah(),
            savedOrder.getTotal(),
            savedOrder.getTglTransaksi()
        );

        // 4. Kirim message ke RabbitMQ Broker
        try {
            rabbitTemplate.convertAndSend(exchange, routingKey, event);
            LOGGER.info("📤 [RABBITMQ] Pesan order berhasil dikirim ke Exchange '{}' with Routing Key '{}'", exchange, routingKey);
        } catch (Exception e) {
            LOGGER.error("❌ Gagal mengirim pesan ke RabbitMQ: {}", e.getMessage());
        }

        return ResponseEntity.ok(savedOrder);
    }
}
