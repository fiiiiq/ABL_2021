package com.zikra.order.entity;

import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long idProduk;
    private String namaProduk;
    private Double hargaProduk;

    private Long idPelanggan;
    private String namaPelanggan;

    private LocalDate tglTransaksi;
    private int jumlah;
    private double total;
    private String email;

    public Order() {
    }

    public Order(Long id, Long idProduk, String namaProduk, Double hargaProduk,
                 Long idPelanggan, String namaPelanggan, LocalDate tglTransaksi,
                 int jumlah, double total, String email) {
        this.id = id;
        this.idProduk = idProduk;
        this.namaProduk = namaProduk;
        this.hargaProduk = hargaProduk;
        this.idPelanggan = idPelanggan;
        this.namaPelanggan = namaPelanggan;
        this.tglTransaksi = tglTransaksi;
        this.jumlah = jumlah;
        this.total = total;
        this.email = email;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

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

    public LocalDate getTglTransaksi() { return tglTransaksi; }
    public void setTglTransaksi(LocalDate tglTransaksi) { this.tglTransaksi = tglTransaksi; }

    public int getJumlah() { return jumlah; }
    public void setJumlah(int jumlah) { this.jumlah = jumlah; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
