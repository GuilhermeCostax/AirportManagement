package com.airportmanagement.application;

import com.airportmanagement.application.port.out.AeroportoRepository;
import com.airportmanagement.application.service.CadastrarAeroportoService;
import com.airportmanagement.domain.model.Aeroporto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class CadastrarAeroportoServiceTest {
  @Test
  void deveRejeitarIataInvalido() {
    AeroportoRepository repo = Mockito.mock(AeroportoRepository.class);
    CadastrarAeroportoService service = new CadastrarAeroportoService(repo);
    Aeroporto a = new Aeroporto(null, "Nome", "AB", "Cidade", null, 0.0, 0.0, 10.0);
    Assertions.assertThrows(IllegalArgumentException.class, () -> service.cadastrar(a));
  }

  @Test
  void deveRejeitarAltitudeNegativa() {
    AeroportoRepository repo = Mockito.mock(AeroportoRepository.class);
    CadastrarAeroportoService service = new CadastrarAeroportoService(repo);
    Aeroporto a = new Aeroporto(null, "Nome", "ABC", "Cidade", null, 0.0, 0.0, -1.0);
    Assertions.assertThrows(IllegalArgumentException.class, () -> service.cadastrar(a));
  }
}

