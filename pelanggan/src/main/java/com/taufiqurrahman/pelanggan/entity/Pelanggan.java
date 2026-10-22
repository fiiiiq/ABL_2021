package com.taufiqurrahman.pelanggan.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Pelanggan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nama_pelanggan;
    private String alamat;
    private String jenis_kelamin;

    public Pelanggan() {
    }

    public Pelanggan(Long id, String nama_pelanggan, String alamat, String jenis_kelamin) {
        this.id = id;
        this.nama_pelanggan = nama_pelanggan;
        this.alamat = alamat;
        this.jenis_kelamin = jenis_kelamin;
    }

     public Long getId() {
        return id;
    }
    public String getNama_pelanggan() {
        return nama_pelanggan;
    }
    public String getAlamat() {
        return alamat;
    }
    public String getJenis_kelamin() {
        return jenis_kelamin;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public void setNama_pelanggan(String nama_pelanggan) {
        this.nama_pelanggan = nama_pelanggan;
    }
    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
    public void setJenis_kelamin(String jenis_kelamin) {
        this.jenis_kelamin = jenis_kelamin;
    }
}