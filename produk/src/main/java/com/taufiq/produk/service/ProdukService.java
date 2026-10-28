package com.taufiq.produk.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taufiq.produk.entity.Produk;
import com.taufiq.produk.repository.ProdukRepository;

@Service
public class ProdukService {

    @Autowired
    private ProdukRepository produkRepository;

    public List<Produk> getAllProduk() {
        return produkRepository.findAll();
    }

    public Produk saveProduk(Produk produk) {
        return produkRepository.save(produk);
    }

    public List<Produk> saveAllProduk(List<Produk> produks) {
        return produkRepository.saveAll(produks);
    }

    public Produk getProdukById(Long id) {
        return produkRepository.findById(id).orElse(null);
    }

    public Produk updateProduk(Long id, Produk produk) {
        Produk existingProduk = produkRepository.findById(id).orElse(null);
        if (existingProduk != null) {
            existingProduk.setNama(produk.getNama());
            existingProduk.setHarga(produk.getHarga());
            existingProduk.setDeskripsi(produk.getDeskripsi());
            existingProduk.setIdjenis(produk.getIdjenis());
            return produkRepository.save(existingProduk);
        }
        return null;
    }

    public void deleteProduk(Long id) {
        produkRepository.deleteById(id);
    }
}
