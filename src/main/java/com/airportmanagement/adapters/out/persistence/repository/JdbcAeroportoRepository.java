package com.airportmanagement.adapters.out.persistence.repository;

import com.airportmanagement.application.port.out.AeroportoRepository;
import com.airportmanagement.domain.model.Aeroporto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcAeroportoRepository implements AeroportoRepository {
  private final JdbcTemplate jdbcTemplate;

  public JdbcAeroportoRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  private static final RowMapper<Aeroporto> mapper = (rs, rowNum) -> new Aeroporto(
    rs.getInt("id_aeroporto"),
    rs.getString("nome_aeroporto"),
    rs.getString("codigo_iata"),
    rs.getString("cidade"),
    rs.getString("codigo_pais_iso"),
    rs.getDouble("latitude"),
    rs.getDouble("longitude"),
    rs.getDouble("altitude")
  );

  @Override
  public List<Aeroporto> findAll() {
    return jdbcTemplate.query("SELECT id_aeroporto, nome_aeroporto, codigo_iata, cidade, codigo_pais_iso, latitude, longitude, altitude FROM aeroporto", mapper);
  }

  @Override
  public Optional<Aeroporto> findByIata(String iata) {
    List<Aeroporto> list = jdbcTemplate.query("SELECT id_aeroporto, nome_aeroporto, codigo_iata, cidade, codigo_pais_iso, latitude, longitude, altitude FROM aeroporto WHERE codigo_iata = ?", mapper, iata);
    return list.stream().findFirst();
  }
}

