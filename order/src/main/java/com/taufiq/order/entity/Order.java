package com.taufiq.order.entity;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long idproduk;
    private Long idpelanggan;
    private Date tgl_order;
    private int jumlah;
    private double total_harga;

    public Order() {
    }

    public Order(Long id, Long idproduk, Long idpelanggan, Date tgl_order, int jumlah, double total_harga) {
        this.id = id;
        this.idproduk = idproduk;
        this.idpelanggan = idpelanggan;
        this.tgl_order = tgl_order;
        this.jumlah = jumlah;
        this.total_harga = total_harga;
    }

    public Long getId() {
        return id;
    }
    public Long getIdproduk() {
        return idproduk;
    }
    public Long getIdpelanggan() {
        return idpelanggan;
    }
    public Date getTgl_order() {
        return tgl_order;
    }
    public int getJumlah() {
        return jumlah;
    }
    public double getTotal_harga() {
        return total_harga;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public void setIdproduk(Long idproduk) {
        this.idproduk = idproduk;
    }
    public void setIdpelanggan(Long idpelanggan) {
        this.idpelanggan = idpelanggan;
    }
    public void setTgl_order(Date tgl_order) {
        this.tgl_order = tgl_order;
    }
    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }
    public void setTotal_harga(double total_harga) {
        this.total_harga = total_harga;
    }

}
