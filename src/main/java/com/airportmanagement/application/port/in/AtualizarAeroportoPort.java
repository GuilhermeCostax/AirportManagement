package com.airportmanagement.application.port.in;

import com.airportmanagement.domain.model.Aeroporto;

public interface AtualizarAeroportoPort {
  Aeroporto atualizar(String iata, Aeroporto dados);
}

