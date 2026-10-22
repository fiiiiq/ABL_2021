package com.taufiq.produk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.taufiq.produk.entity.Jenis;

@Repository
public interface JenisRepository extends JpaRepository<Jenis, Long> {

}
