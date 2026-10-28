package com.taufiq.email.service;

import com.taufiq.email.dto.OrderEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.util.Locale;

@Service
public class EmailConsumerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailConsumerService.class);

    @Autowired(required = false)
    private JavaMailSender mailSender;

    @Value("${spring.mail.username:}")
    private String mailSenderAddress;

    @Value("${spring.mail.password:}")
    private String mailSenderPassword;

    @Value("${app.mail.default-recipient:}")
    private String defaultRecipient;

    @RabbitListener(queues = "${rabbitmq.queue.order}")
    public void handleOrderEvent(OrderEvent orderEvent) {
        LOGGER.info("==================================================================");
        LOGGER.info("📥 [RABBITMQ CONSUMER] Menerima event order dari RabbitMQ!");
        LOGGER.info("📦 Order ID: {}", orderEvent.getOrderId());
        LOGGER.info("📦 Produk: {}", orderEvent.getNamaProduk());
        LOGGER.info("📦 Pelanggan: {}", orderEvent.getNamaPelanggan());
        LOGGER.info("📦 Jumlah: {}, Total: {}", orderEvent.getJumlah(), orderEvent.getTotal());
        LOGGER.info("==================================================================");

        // Tentukan email penerima
        String recipient = orderEvent.getEmailPelanggan();
        if (recipient == null || recipient.trim().isEmpty() || recipient.contains("ganti_dengan")) {
            recipient = defaultRecipient;
        }

        // Format mata uang Rupiah
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        String formattedTotal = currencyFormat.format(orderEvent.getTotal());
        String formattedHarga = orderEvent.getHargaProduk() != null 
                ? currencyFormat.format(orderEvent.getHargaProduk()) 
                : "-";

        String subject = "🔔 [NOTIFIKASI ORDER] Pesanan #" + orderEvent.getOrderId() + " Berhasil Dibuat";
        String body = String.format(
                "Halo %s,\n\n" +
                "Terima kasih telah melakukan pemesanan. Berikut rincian order Anda:\n" +
                "--------------------------------------------------\n" +
                "Nomor Order      : #%d\n" +
                "Tanggal Transaksi: %s\n" +
                "Nama Pelanggan   : %s\n" +
                "Produk           : %s\n" +
                "Harga Satuan     : %s\n" +
                "Jumlah           : %d\n" +
                "Total Pembayaran : %s\n" +
                "--------------------------------------------------\n\n" +
                "Pesan ini dikirimkan otomatis oleh Email Service via RabbitMQ.\n",
                orderEvent.getNamaPelanggan(),
                orderEvent.getOrderId(),
                orderEvent.getTglTransaksi(),
                orderEvent.getNamaPelanggan(),
                orderEvent.getNamaProduk(),
                formattedHarga,
                orderEvent.getJumlah(),
                formattedTotal
        );

        sendEmail(recipient, subject, body);
    }

    private void sendEmail(String toEmail, String subject, String content) {
        if (toEmail == null || toEmail.trim().isEmpty() || toEmail.contains("ganti_dengan")) {
            LOGGER.warn("⚠️ Email tujuan belum diatur! Isi 'email' di request order atau atur 'app.mail.default-recipient' di application.properties.");
            printEmailPreview(toEmail, subject, content);
            return;
        }

        if (mailSenderAddress == null || mailSenderAddress.trim().isEmpty() || mailSenderAddress.contains("ganti_dengan") ||
            mailSenderPassword == null || mailSenderPassword.trim().isEmpty() || mailSenderPassword.contains("ganti_dengan") ||
            mailSender == null) {
            LOGGER.info("ℹ️ Pengiriman SMTP riil dilewati (Sandi SMTP kosong/belum diisi).");
            LOGGER.info("ℹ️ Alur RabbitMQ -> Consumer SUKSES! Menampilkan isi pesan email:");
            printEmailPreview(toEmail, subject, content);
            return;
        }

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(mailSenderAddress);
            message.setTo(toEmail);
            message.setSubject(subject);
            message.setText(content);

            LOGGER.info("📧 Sedang mengirim email nyata ke {} via SMTP...", toEmail);
            mailSender.send(message);
            LOGGER.info("✅ EMAIL BERHASIL TERKIRIM KE: {}", toEmail);
        } catch (Exception e) {
            LOGGER.error("❌ Gagal mengirim email via SMTP: " + e.getMessage(), e);
            LOGGER.warn("Pastikan Anda menggunakan 'App Password' Gmail (16 digit), bukan password akun biasa.");
            printEmailPreview(toEmail, subject, content);
        }
    }

    private void printEmailPreview(String toEmail, String subject, String content) {
        LOGGER.info("\n--- [PREVIEW KONTEN EMAIL YANG DIKIRIM] ---\n" +
                "To: {}\n" +
                "Subject: {}\n\n" +
                "{}\n" +
                "-------------------------------------------", toEmail, subject, content);
    }
}
