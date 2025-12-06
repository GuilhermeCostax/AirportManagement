package com.airportmanagement.application;

import com.airportmanagement.application.port.out.AeroportoRepository;
import com.airportmanagement.application.service.ConsultarAeroportosService;
import com.airportmanagement.shared.exception.AeroportoNaoEncontradoException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

public class ConsultarAeroportosServiceTest {
  @Test
  void deveLancarErroQuandoIataInvalido() {
    AeroportoRepository repo = Mockito.mock(AeroportoRepository.class);
    ConsultarAeroportosService service = new ConsultarAeroportosService(repo);
    Assertions.assertThrows(IllegalArgumentException.class, () -> service.porCodigoIata("AB"));
  }

  @Test
  void deveLancarErroQuandoAeroportoNaoEncontrado() {
    AeroportoRepository repo = Mockito.mock(AeroportoRepository.class);
    Mockito.when(repo.findByIata("AAA")).thenReturn(Optional.empty());
    ConsultarAeroportosService service = new ConsultarAeroportosService(repo);
    Assertions.assertThrows(AeroportoNaoEncontradoException.class, () -> service.porCodigoIata("AAA"));
  }
}

