package com.taufiq.produk.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Produk {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_produk;
    private String nama;
    private String deskripsi;
    private double harga;
    private Long idjenis;

    public Produk() {
    }

    
    public Produk(Long id_produk, String nama, String deskripsi, double harga, Long idjenis) {
        this.id_produk = id_produk;
        this.nama = nama;
        this.deskripsi = deskripsi;
        this.harga = harga;
        this.idjenis = idjenis;
    }

    public Long getId_produk() {
        return id_produk;
    }
    public String getNama() {
        return nama;
    }
    public String getDeskripsi() {
        return deskripsi;
    }
    public double getHarga() {
        return harga;
    }
    public Long getIdjenis() {
        return idjenis;
    }

    public void setId_produk(Long id_produk) {
        this.id_produk = id_produk;
    }
    public void setNama(String nama) {
        this.nama = nama;
    }
    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }
    public void setHarga(double harga) {
        this.harga = harga;
    }
    public void setIdjenis(Long idjenis) {
        this.idjenis = idjenis;
    }
}
