package com.zikra.order.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDate;

public class OrderDetailResponse {

    private Long id;
    private LocalDate tglTransaksi;
    private int jumlah;
    private double total;

    private ProdukDTO produk;
    private PelangganDTO pelanggan;

    public OrderDetailResponse() {}

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDate getTglTransaksi() { return tglTransaksi; }
    public void setTglTransaksi(LocalDate tglTransaksi) { this.tglTransaksi = tglTransaksi; }
    public int getJumlah() { return jumlah; }
    public void setJumlah(int jumlah) { this.jumlah = jumlah; }
    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
    public ProdukDTO getProduk() { return produk; }
    public void setProduk(ProdukDTO produk) { this.produk = produk; }
    public PelangganDTO getPelanggan() { return pelanggan; }
    public void setPelanggan(PelangganDTO pelanggan) { this.pelanggan = pelanggan; }

    // ----- Inner DTO: Produk -----
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ProdukDTO {
        private Long id;
        private String nama;
        private Double harga;
        private String deskripsi;

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getNama() { return nama; }
        public void setNama(String nama) { this.nama = nama; }
        public Double getHarga() { return harga; }
        public void setHarga(Double harga) { this.harga = harga; }
        public String getDeskripsi() { return deskripsi; }
        public void setDeskripsi(String deskripsi) { this.deskripsi = deskripsi; }
    }

    // ----- Inner DTO: Pelanggan -----
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PelangganDTO {
        private Long id;
        private String nama;
        private String alamat;
        private String jenisKelamin;

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getNama() { return nama; }
        public void setNama(String nama) { this.nama = nama; }
        public String getAlamat() { return alamat; }
        public void setAlamat(String alamat) { this.alamat = alamat; }
        public String getJenisKelamin() { return jenisKelamin; }
        public void setJenisKelamin(String jenisKelamin) { this.jenisKelamin = jenisKelamin; }
    }
}
