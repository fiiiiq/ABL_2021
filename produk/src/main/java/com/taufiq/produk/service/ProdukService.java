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
    public List<Produk> getAllproduk() {
        return produkRepository.findAll();
    }
    public Produk SaveProduk(Produk produk){
        return produkRepository.save(produk);
    }
    public Produk getProdukById(Long id) {
        return produkRepository.findById(id).orElse(null);
    }
    public void deleteProduk(Long id) {
        produkRepository.deleteById(id);
    }
    public Produk updateProduk(Long id, Produk produk){
        Produk existingProduk = produkRepository.findById(id).orElse(null);
        if (existingProduk != null) {
            existingProduk.setNama(produk.getNama());
            existingProduk.setDeskripsi(produk.getDeskripsi());
            existingProduk.setHarga(produk.getHarga());
            return produkRepository.save(existingProduk);
        }
        return null;
    }

    public List<Produk> getProdukByJenis(Long idjenis) {
        return produkRepository.findByIdjenis(idjenis);
    }

}
