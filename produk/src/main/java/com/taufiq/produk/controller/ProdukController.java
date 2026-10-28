package com.taufiq.produk.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taufiq.produk.entity.Jenis;
import com.taufiq.produk.entity.Produk;
import com.taufiq.produk.repository.JenisRepository;
import com.taufiq.produk.service.ProdukService;

@RestController
@RequestMapping("/api/produk")
public class ProdukController {

    @Autowired
    ProdukService produkService;

    @Autowired
    private JenisRepository jenisRepository;

    @GetMapping
    public List<Produk> getAllProduk() {
        List<Produk> produks = produkService.getAllProduk();
        return produks;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produk> getProdukById(@PathVariable("id") Long id) {
        Produk produk = produkService.getProdukById(id);
        if (produk != null) {
            return ResponseEntity.ok(produk);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Produk> createProduk(@RequestBody Produk produk) {
        Produk savedProduk = produkService.saveProduk(produk);
        return ResponseEntity.ok(savedProduk);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produk> updateProduk(@PathVariable("id") Long id, @RequestBody Produk produk) {
        Produk updatedProduk = produkService.updateProduk(id, produk);
        if (updatedProduk != null) {
            return ResponseEntity.ok(updatedProduk);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduk(@PathVariable("id") Long id) {
        produkService.deleteProduk(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/pilih/{pil}")
    public String ping(@PathVariable("pil") String pil) {
        String namaProduk = pil;
        if ("1".equals(pil)) {
            namaProduk = "mouse";
        } else if ("2".equals(pil)) {
            namaProduk = "keyboard";
        }
        return "Pilihan anda : " + namaProduk;
    }

    @GetMapping("/jumlah/{angka1}/{angka2}")
    public String jumlah(@PathVariable("angka1") int angka1, @PathVariable("angka2") int angka2) {
        int hasil = angka1 + angka2;
        return "Hasil penjumlahan " + angka1 + " + " + angka2 + " = " + hasil;
    }

    // Endpoint Konek Database (dipanggil via Repository)
    @GetMapping("/jenis/{id}")
    public ResponseEntity<Jenis> getJenisById(@PathVariable("id") Long id) {
        Jenis jenis = jenisRepository.findById(id);
        if (jenis != null) {
            return ResponseEntity.ok(jenis);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/jenis")
    public List<Jenis> getAllJenis() {
        return jenisRepository.findAll();
    }

    @PostMapping("/jenis")
    public ResponseEntity<Jenis> createJenis(@RequestBody Jenis jenis) {
        Jenis savedJenis = jenisRepository.save(jenis);
        return ResponseEntity.ok(savedJenis);
    }

}
