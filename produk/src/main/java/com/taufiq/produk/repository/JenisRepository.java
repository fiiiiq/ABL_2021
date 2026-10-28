package com.taufiq.produk.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.taufiq.produk.entity.Jenis;

@Repository
public class JenisRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Jenis findById(Long id) {
        try {
            String sql = "SELECT id, jenis FROM jenis WHERE id = ?";
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
                new Jenis(rs.getLong("id"), rs.getString("jenis")), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Jenis> findAll() {
        String sql = "SELECT id, jenis FROM jenis";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
            new Jenis(rs.getLong("id"), rs.getString("jenis"))
        );
    }

    public Jenis save(Jenis jenis) {
        if (jenis.getId() != null) {
            String sql = "INSERT INTO jenis (id, jenis) VALUES (?, ?)";
            jdbcTemplate.update(sql, jenis.getId(), jenis.getJenis());
        } else {
            String sql = "INSERT INTO jenis (jenis) VALUES (?)";
            jdbcTemplate.update(sql, jenis.getJenis());
        }
        return jenis;
    }
}
