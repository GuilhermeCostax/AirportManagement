package com.airportmanagement.application.service;

import com.airportmanagement.application.port.in.ConsultarAeroportosPort;
import com.airportmanagement.application.port.out.AeroportoRepository;
import com.airportmanagement.domain.model.Aeroporto;
import com.airportmanagement.shared.exception.AeroportoNaoEncontradoException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultarAeroportosService implements ConsultarAeroportosPort {
  private final AeroportoRepository repository;

  public ConsultarAeroportosService(AeroportoRepository repository) {
    this.repository = repository;
  }

  @Override
  public List<Aeroporto> listarTodos() {
    return repository.findAll();
  }

  @Override
  public Aeroporto porCodigoIata(String iata) {
    if (iata == null || iata.length() != 3) throw new IllegalArgumentException("codigo_iata inválido");
    String code = iata.toUpperCase();
    return repository.findByIata(code).orElseThrow(() -> new AeroportoNaoEncontradoException(code));
  }
}

