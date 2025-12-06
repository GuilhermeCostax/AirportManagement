package com.airportmanagement.application.port.in;

import com.airportmanagement.domain.model.Aeroporto;
import java.util.List;

public interface ConsultarAeroportosPort {
  List<Aeroporto> listarTodos();
  Aeroporto porCodigoIata(String iata);
}

