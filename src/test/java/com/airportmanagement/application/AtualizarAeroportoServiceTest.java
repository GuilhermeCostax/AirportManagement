package com.airportmanagement.application;

import com.airportmanagement.application.port.out.AeroportoRepository;
import com.airportmanagement.application.service.AtualizarAeroportoService;
import com.airportmanagement.domain.model.Aeroporto;
import com.airportmanagement.shared.exception.AeroportoNaoEncontradoException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

public class AtualizarAeroportoServiceTest {
  @Test
  void deveValidarIataDoPath() {
    AeroportoRepository repo = Mockito.mock(AeroportoRepository.class);
    AtualizarAeroportoService service = new AtualizarAeroportoService(repo);
    Aeroporto a = new Aeroporto(null, "Nome", "AB", "Cidade", null, 1.0, 2.0, 3.0);
    Assertions.assertThrows(IllegalArgumentException.class, () -> service.atualizar("AB", a));
  }

  @Test
  void deveLancarNaoEncontrado() {
    AeroportoRepository repo = Mockito.mock(AeroportoRepository.class);
    Mockito.when(repo.findByIata("ABC")).thenReturn(Optional.empty());
    AtualizarAeroportoService service = new AtualizarAeroportoService(repo);
    Aeroporto a = new Aeroporto(null, "Nome", "ABC", "Cidade", null, 1.0, 2.0, 3.0);
    Assertions.assertThrows(AeroportoNaoEncontradoException.class, () -> service.atualizar("ABC", a));
  }

  @Test
  void deveRejeitarAltitudeNegativa() {
    AeroportoRepository repo = Mockito.mock(AeroportoRepository.class);
    Mockito.when(repo.findByIata("ABC")).thenReturn(Optional.of(new Aeroporto(1, "X", "ABC", "Y", null, 0.0, 0.0, 0.0)));
    AtualizarAeroportoService service = new AtualizarAeroportoService(repo);
    Aeroporto a = new Aeroporto(null, "Nome", "ABC", "Cidade", null, 1.0, 2.0, -1.0);
    Assertions.assertThrows(IllegalArgumentException.class, () -> service.atualizar("ABC", a));
  }
}

