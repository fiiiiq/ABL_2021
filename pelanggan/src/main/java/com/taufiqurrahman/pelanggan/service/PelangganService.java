package com.taufiqurrahman.pelanggan.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.taufiqurrahman.pelanggan.entity.Pelanggan;
import com.taufiqurrahman.pelanggan.repository.PelangganRepository;

@Service 
public class PelangganService {
    @Autowired 
    private PelangganRepository pelangganRepository;
    public List<Pelanggan> getAllPelanggan() {
        return pelangganRepository.findAll();
    }
    public Pelanggan savePelanggan(Pelanggan pelanggan) {
        return pelangganRepository.save(pelanggan);
    }
    public Pelanggan getPelangganById(Long id) {
        return pelangganRepository.findById(id).orElse(null);
    }
    public void deletePelanggan(Long id) {
        pelangganRepository.deleteById(id);
    }
    public Pelanggan updatePelanggan(Long id, Pelanggan pelangganBaru){
        Pelanggan existingPelanggan = pelangganRepository.findById(id).orElse(null);
        if (existingPelanggan != null) {
            existingPelanggan.setNama_pelanggan(pelangganBaru.getNama_pelanggan());
            existingPelanggan.setAlamat(pelangganBaru.getAlamat());
            existingPelanggan.setJenis_kelamin(pelangganBaru.getJenis_kelamin());
            return pelangganRepository.save(existingPelanggan);
        }
        return null;
    }
}
