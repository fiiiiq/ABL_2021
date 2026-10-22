package com.taufiqurrahman.pelanggan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.taufiqurrahman.pelanggan.entity.Pelanggan;
import com.taufiqurrahman.pelanggan.service.PelangganService;

@RestController
public class PelangganController {

    @Autowired
    private PelangganService pelangganService;

    @GetMapping("/api/pelanggan")
    public List<Pelanggan> getAllPelanggan() {
        return pelangganService.getAllPelanggan();
    }

    @GetMapping("/api/pelanggan/{id}")
    public ResponseEntity<Pelanggan> getPelangganById(@PathVariable Long id) {

        Pelanggan pelanggan = pelangganService.getPelangganById(id);

        if (pelanggan != null) {
            return ResponseEntity.ok(pelanggan);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/api/pelanggan")
    public ResponseEntity<Pelanggan> createPelanggan(
            @RequestBody Pelanggan pelanggan) {

        return ResponseEntity.ok(
                pelangganService.savePelanggan(pelanggan)
        );
    }

    @PutMapping("/api/pelanggan/{id}")
    public ResponseEntity<Pelanggan> updatePelanggan(
            @PathVariable Long id,
            @RequestBody Pelanggan pelanggan) {

        Pelanggan updatedPelanggan =
                pelangganService.updatePelanggan(id, pelanggan);

        if (updatedPelanggan != null) {
            return ResponseEntity.ok(updatedPelanggan);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/api/pelanggan/{id}")
    public ResponseEntity<Void> deletePelanggan(
            @PathVariable Long id) {

        Pelanggan pelanggan =
                pelangganService.getPelangganById(id);

        if (pelanggan == null) {
            return ResponseEntity.notFound().build();
        }

        pelangganService.deletePelanggan(id);

        return ResponseEntity.noContent().build();
    }
}