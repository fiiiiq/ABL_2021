package com.taufiq.consumer;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import tools.jackson.core.JacksonException;
import tools.jackson.databind.json.JsonMapper;

/**
 * Listener ASYNCHRONOUS: menerima event order dari RabbitMQ lalu mengirim email ke pelanggan.
 */
@Component
public class OrderEmailListener {

    private static final Logger log = LoggerFactory.getLogger(OrderEmailListener.class);
    private static final DecimalFormat RUPIAH_FORMAT = new DecimalFormat(
            "'Rp'#,##0.00",
            new DecimalFormatSymbols(Locale.of("id", "ID")));

    private final EmailService emailService;
    private final JsonMapper jsonMapper;

    public OrderEmailListener(EmailService emailService, JsonMapper jsonMapper) {
        this.emailService = emailService;
        this.jsonMapper = jsonMapper;
    }

    @RabbitListener(queues = "${app.rabbitmq.email-queue}")
    public void onOrderEvent(String payload) {
        OrderNotification n;
        try {
            n = jsonMapper.readValue(payload, OrderNotification.class);
        } catch (JacksonException e) {
            // Payload rusak tidak akan pernah berhasil -> jangan di-retry
            throw new AmqpRejectAndDontRequeueException("Payload notifikasi tidak valid: " + payload, e);
        }

        log.info("[EMAIL][ASYNC][{}] Menerima event {} untuk order #{}",
                Thread.currentThread().getName(), n.getEventType(), n.getOrderId());

        emailService.send(n.getEmail(), buildSubject(n), buildBody(n));
    }

    private String buildSubject(OrderNotification n) {
        String action = switch (n.getEventType() == null ? "" : n.getEventType()) {
            case "CREATED" -> "Pesanan #" + n.getOrderId() + " Berhasil Dibuat";
            case "UPDATED" -> "Pesanan #" + n.getOrderId() + " Berhasil Diperbarui";
            case "DELETED" -> "Pesanan #" + n.getOrderId() + " Dibatalkan";
            default -> "Informasi Pesanan #" + n.getOrderId();
        };
        return "🔔 [NOTIFIKASI ORDER] " + action;
    }

    private String buildBody(OrderNotification n) {
        String nama = n.getNamaPelanggan() != null ? n.getNamaPelanggan() : "Pelanggan";
        String produk = n.getNamaProduk() != null ? n.getNamaProduk() : "Produk #" + n.getProdukId();
        double hargaSatuan = (n.getHargaProduk() != null && n.getHargaProduk() > 0)
                ? n.getHargaProduk()
                : (n.getJumlah() > 0 ? n.getTotal() / n.getJumlah() : 0);

        return """
                Halo %s,

                Terima kasih telah melakukan pemesanan. Berikut rincian order Anda:
                ---------------------------------------------
                Nomor Order    : #%d
                Tanggal Transaksi: %s
                Nama Pelanggan  : %s
                Produk         : %s
                Harga Satuan   : %s
                Jumlah         : %d
                Total Pembayaran : %s
                ---------------------------------------------

                Pesan ini dikirimkan otomatis oleh Email Service via RabbitMQ.
                """.formatted(
                nama,
                n.getOrderId(),
                n.getTanggalTransaksi(),
                nama,
                produk,
                RUPIAH_FORMAT.format(hargaSatuan),
                n.getJumlah(),
                RUPIAH_FORMAT.format(n.getTotal()));
    }
}
