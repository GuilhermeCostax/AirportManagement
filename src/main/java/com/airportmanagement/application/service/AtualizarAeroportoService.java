package com.airportmanagement.application.service;

import com.airportmanagement.application.port.in.AtualizarAeroportoPort;
import com.airportmanagement.application.port.out.AeroportoRepository;
import com.airportmanagement.domain.model.Aeroporto;
import com.airportmanagement.shared.exception.AeroportoNaoEncontradoException;
import org.springframework.stereotype.Service;

@Service
public class AtualizarAeroportoService implements AtualizarAeroportoPort {
  private final AeroportoRepository repository;

  public AtualizarAeroportoService(AeroportoRepository repository) {
    this.repository = repository;
  }

  @Override
  public Aeroporto atualizar(String iata, Aeroporto dados) {
    if (iata == null || iata.length() != 3) throw new IllegalArgumentException("codigo_iata inválido");
    String code = iata.toUpperCase();
    repository.findByIata(code).orElseThrow(() -> new AeroportoNaoEncontradoException(code));
    validar(dados);
    Aeroporto toUpdate = new Aeroporto(null, dados.getNomeAeroporto(), code, dados.getCidade(), dados.getCodigoPaisIso(), dados.getLatitude(), dados.getLongitude(), dados.getAltitude());
    int rows = repository.updateByIata(code, toUpdate);
    if (rows == 0) throw new AeroportoNaoEncontradoException(code);
    return repository.findByIata(code).orElseThrow(() -> new AeroportoNaoEncontradoException(code));
  }

  private void validar(Aeroporto a) {
    if (a.getNomeAeroporto() == null || a.getNomeAeroporto().trim().isEmpty()) throw new IllegalArgumentException("nome_aeroporto obrigatório");
    if (a.getCidade() == null || a.getCidade().trim().isEmpty()) throw new IllegalArgumentException("cidade obrigatória");
    if (a.getAltitude() != null && a.getAltitude() < 0) throw new IllegalArgumentException("altitude não pode ser negativa");
  }
}

