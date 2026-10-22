package com.taufiq.produk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.taufiq.produk.entity.Produk;
import java.util.List;

@Repository
public interface ProdukRepository extends JpaRepository<Produk, Long>
{
     List<Produk> findByIdjenis(Long idjenis);
}
