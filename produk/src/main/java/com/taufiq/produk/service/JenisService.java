package com.taufiq.produk.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.taufiq.produk.entity.Jenis;
import com.taufiq.produk.repository.JenisRepository;

@Service 
public class JenisService {
    @Autowired 
    private JenisRepository jenisRepository;
    public List<Jenis> getAllJenis() {
        return jenisRepository.findAll();
    }
    public Jenis SaveJenis(Jenis jenisBarang){
        return jenisRepository.save(jenisBarang);
    }
    public Jenis getJenisById(Long id) {
        return jenisRepository.findById(id).orElse(null);
    }
    public void deleteJenis(Long id) {
        jenisRepository.deleteById(id);
    }
    public Jenis updateJenis(Long id, Jenis jenisBarang){
        Jenis existingJenis = jenisRepository.findById(id).orElse(null);
        if (existingJenis != null) {
            existingJenis.setNama(jenisBarang.getNama());
            return jenisRepository.save(existingJenis);
        }
        return null;
    }
}
