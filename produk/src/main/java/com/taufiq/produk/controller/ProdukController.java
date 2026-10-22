package com.taufiq.produk.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.taufiq.produk.entity.Produk;
import com.taufiq.produk.entity.Jenis;
import com.taufiq.produk.service.ProdukService;
import com.taufiq.produk.service.JenisService;

@RestController
public class ProdukController {

    @Autowired
    ProdukService produkService;

    @Autowired
    JenisService jenisService;

    @GetMapping("/api/produk")
    public List<Produk> getAllProduk() {
        return produkService.getAllproduk();
    }

    @GetMapping("/api/produk/{id}")
    public ResponseEntity<Produk> getProdukById(@PathVariable Long id) {

        Produk produk = produkService.getProdukById(id);

        if (produk != null) {
            return ResponseEntity.ok(produk);
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/api/produk/jenis/{idjenis}")
    public List<Produk> getProdukByIdjenis(@PathVariable Long idjenis) {
        return produkService.getProdukByJenis(idjenis);
    }

    @PostMapping("/api/produk")
    public ResponseEntity<Produk> createProduk(@RequestBody Produk produk) {
        return ResponseEntity.ok(produkService.SaveProduk(produk));
    }


    @GetMapping("/api/jenis")
    public List<Jenis> getAllJenis() {
        return jenisService.getAllJenis();
    }

    @GetMapping("/api/jenis/{idjenis}")
    public ResponseEntity<Jenis> getJenisById(@PathVariable Long idjenis) {

        Jenis jenis = jenisService.getJenisById(idjenis);

        if (jenis != null) {
            return ResponseEntity.ok(jenis);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/api/jenis")
    public ResponseEntity<Jenis> createJenis(@RequestBody Jenis jenis) {
        return ResponseEntity.ok(jenisService.SaveJenis(jenis));
    }
}