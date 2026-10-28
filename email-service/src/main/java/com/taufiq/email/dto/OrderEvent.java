package com.taufiq.email.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderEvent implements Serializable {

    private Long orderId;
    private Long idProduk;
    private String namaProduk;
    private Double hargaProduk;
    private Long idPelanggan;
    private String namaPelanggan;
    private String emailPelanggan;
    private int jumlah;
    private double total;
    private LocalDate tglTransaksi;

    public OrderEvent() {
    }

    public OrderEvent(Long orderId, Long idProduk, String namaProduk, Double hargaProduk,
                      Long idPelanggan, String namaPelanggan, String emailPelanggan,
                      int jumlah, double total, LocalDate tglTransaksi) {
        this.orderId = orderId;
        this.idProduk = idProduk;
        this.namaProduk = namaProduk;
        this.hargaProduk = hargaProduk;
        this.idPelanggan = idPelanggan;
        this.namaPelanggan = namaPelanggan;
        this.emailPelanggan = emailPelanggan;
        this.jumlah = jumlah;
        this.total = total;
        this.tglTransaksi = tglTransaksi;
    }

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }
    public Long getIdProduk() { return idProduk; }
    public void setIdProduk(Long idProduk) { this.idProduk = idProduk; }
    public String getNamaProduk() { return namaProduk; }
    public void setNamaProduk(String namaProduk) { this.namaProduk = namaProduk; }
    public Double getHargaProduk() { return hargaProduk; }
    public void setHargaProduk(Double hargaProduk) { this.hargaProduk = hargaProduk; }
    public Long getIdPelanggan() { return idPelanggan; }
    public void setIdPelanggan(Long idPelanggan) { this.idPelanggan = idPelanggan; }
    public String getNamaPelanggan() { return namaPelanggan; }
    public void setNamaPelanggan(String namaPelanggan) { this.namaPelanggan = namaPelanggan; }
    public String getEmailPelanggan() { return emailPelanggan; }
    public void setEmailPelanggan(String emailPelanggan) { this.emailPelanggan = emailPelanggan; }
    public int getJumlah() { return jumlah; }
    public void setJumlah(int jumlah) { this.jumlah = jumlah; }
    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
    public LocalDate getTglTransaksi() { return tglTransaksi; }
    public void setTglTransaksi(LocalDate tglTransaksi) { this.tglTransaksi = tglTransaksi; }

    @Override
    public String toString() {
        return "OrderEvent{" +
                "orderId=" + orderId +
                ", namaProduk='" + namaProduk + '\'' +
                ", namaPelanggan='" + namaPelanggan + '\'' +
                ", emailPelanggan='" + emailPelanggan + '\'' +
                ", jumlah=" + jumlah +
                ", total=" + total +
                ", tglTransaksi=" + tglTransaksi +
                '}';
    }
}
