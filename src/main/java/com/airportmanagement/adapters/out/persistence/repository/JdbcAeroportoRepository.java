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

  @Override
  public Aeroporto save(Aeroporto aeroporto) {
    var sql = "INSERT INTO aeroporto (nome_aeroporto, codigo_iata, cidade, codigo_pais_iso, latitude, longitude, altitude) VALUES (?,?,?,?,?,?,?)";
    var kh = new org.springframework.jdbc.support.GeneratedKeyHolder();
    jdbcTemplate.update(con -> {
      var ps = con.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS);
      ps.setString(1, aeroporto.getNomeAeroporto());
      ps.setString(2, aeroporto.getCodigoIata());
      ps.setString(3, aeroporto.getCidade());
      ps.setString(4, aeroporto.getCodigoPaisIso());
      if (aeroporto.getLatitude() == null) ps.setNull(5, java.sql.Types.DOUBLE); else ps.setDouble(5, aeroporto.getLatitude());
      if (aeroporto.getLongitude() == null) ps.setNull(6, java.sql.Types.DOUBLE); else ps.setDouble(6, aeroporto.getLongitude());
      if (aeroporto.getAltitude() == null) ps.setNull(7, java.sql.Types.DOUBLE); else ps.setDouble(7, aeroporto.getAltitude());
      return ps;
    }, kh);
    Integer id = kh.getKey() != null ? kh.getKey().intValue() : null;
    return new Aeroporto(id, aeroporto.getNomeAeroporto(), aeroporto.getCodigoIata(), aeroporto.getCidade(), aeroporto.getCodigoPaisIso(), aeroporto.getLatitude(), aeroporto.getLongitude(), aeroporto.getAltitude());
  }

  @Override
  public int updateByIata(String iata, Aeroporto aeroporto) {
    var sql = "UPDATE aeroporto SET nome_aeroporto = ?, cidade = ?, codigo_pais_iso = ?, latitude = ?, longitude = ?, altitude = ? WHERE codigo_iata = ?";
    return jdbcTemplate.update(sql,
      aeroporto.getNomeAeroporto(),
      aeroporto.getCidade(),
      aeroporto.getCodigoPaisIso(),
      aeroporto.getLatitude(),
      aeroporto.getLongitude(),
      aeroporto.getAltitude(),
      iata
    );
  }

  @Override
  public int deleteByIata(String iata) {
    return jdbcTemplate.update("DELETE FROM aeroporto WHERE codigo_iata = ?", iata);
  }
}
