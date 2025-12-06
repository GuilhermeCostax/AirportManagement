package com.airportmanagement.application.service;

import com.airportmanagement.application.port.in.ExcluirAeroportoPort;
import com.airportmanagement.application.port.out.AeroportoRepository;
import com.airportmanagement.shared.exception.AeroportoNaoEncontradoException;
import org.springframework.stereotype.Service;

@Service
public class ExcluirAeroportoService implements ExcluirAeroportoPort {
  private final AeroportoRepository repository;

  public ExcluirAeroportoService(AeroportoRepository repository) {
    this.repository = repository;
  }

  @Override
  public void excluirPorIata(String iata) {
    if (iata == null || iata.length() != 3) throw new IllegalArgumentException("codigo_iata inválido");
    String code = iata.toUpperCase();
    repository.findByIata(code).orElseThrow(() -> new AeroportoNaoEncontradoException(code));
    int rows = repository.deleteByIata(code);
    if (rows == 0) throw new AeroportoNaoEncontradoException(code);
  }
}

