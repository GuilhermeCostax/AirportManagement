package com.airportmanagement.application;

import com.airportmanagement.application.port.out.AeroportoRepository;
import com.airportmanagement.application.service.ExcluirAeroportoService;
import com.airportmanagement.domain.model.Aeroporto;
import com.airportmanagement.shared.exception.AeroportoNaoEncontradoException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

public class ExcluirAeroportoServiceTest {
  @Test
  void deveValidarIata() {
    ExcluirAeroportoService service = new ExcluirAeroportoService(Mockito.mock(AeroportoRepository.class));
    Assertions.assertThrows(IllegalArgumentException.class, () -> service.excluirPorIata("AB"));
  }

  @Test
  void deveLancarNaoEncontrado() {
    AeroportoRepository repo = Mockito.mock(AeroportoRepository.class);
    Mockito.when(repo.findByIata("AAA")).thenReturn(Optional.empty());
    ExcluirAeroportoService service = new ExcluirAeroportoService(repo);
    Assertions.assertThrows(AeroportoNaoEncontradoException.class, () -> service.excluirPorIata("AAA"));
  }

  @Test
  void deveExcluirQuandoExiste() {
    AeroportoRepository repo = Mockito.mock(AeroportoRepository.class);
    Mockito.when(repo.findByIata("MAG")).thenReturn(Optional.of(new Aeroporto(2, "Madang Airport", "MAG", "Madang", null, -5.2, 145.78, 20.0)));
    Mockito.when(repo.deleteByIata("MAG")).thenReturn(1);
    ExcluirAeroportoService service = new ExcluirAeroportoService(repo);
    Assertions.assertDoesNotThrow(() -> service.excluirPorIata("MAG"));
  }
}

