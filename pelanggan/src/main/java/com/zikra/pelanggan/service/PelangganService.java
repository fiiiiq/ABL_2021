package com.zikra.pelanggan.service;

import com.zikra.pelanggan.entity.Pelanggan;
import com.zikra.pelanggan.repository.PelangganRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PelangganService {

    @Autowired
    private PelangganRepository pelangganRepository;

    public List<Pelanggan> getAllPelanggan() { return pelangganRepository.findAll(); }
    public Pelanggan savePelanggan(Pelanggan pelanggan) { return pelangganRepository.save(pelanggan); }
    public Pelanggan getPelangganById(Long id) { return pelangganRepository.findById(id).orElse(null); }
}
