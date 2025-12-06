package com.airportmanagement.application.port.out;

import com.airportmanagement.domain.model.Aeroporto;
import java.util.List;
import java.util.Optional;

public interface AeroportoRepository {
  List<Aeroporto> findAll();
  Optional<Aeroporto> findByIata(String iata);
  Aeroporto save(Aeroporto aeroporto);
}
