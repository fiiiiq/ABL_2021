package com.taufiq.produk.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Jenis {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idjenis;
    private String nama;


    public Jenis() {
    }

    public Jenis(Long idjenis, String nama) {
        this.idjenis = idjenis;
        this.nama = nama;
    }

    public Long getIdjenis() {
        return idjenis;
    }

    public String getNama() {
        return nama;
    }

    public void setIdjenis(Long idjenis) {
        this.idjenis = idjenis;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
}
