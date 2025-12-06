package com.airportmanagement.application.service;

import com.airportmanagement.application.port.in.CadastrarAeroportoPort;
import com.airportmanagement.application.port.out.AeroportoRepository;
import com.airportmanagement.domain.model.Aeroporto;
import org.springframework.stereotype.Service;

@Service
public class CadastrarAeroportoService implements CadastrarAeroportoPort {
  private final AeroportoRepository repository;

  public CadastrarAeroportoService(AeroportoRepository repository) {
    this.repository = repository;
  }

  @Override
  public Aeroporto cadastrar(Aeroporto aeroporto) {
    validar(aeroporto);
    return repository.save(new Aeroporto(
      null,
      aeroporto.getNomeAeroporto(),
      aeroporto.getCodigoIata().toUpperCase(),
      aeroporto.getCidade(),
      aeroporto.getCodigoPaisIso(),
      aeroporto.getLatitude(),
      aeroporto.getLongitude(),
      aeroporto.getAltitude()
    ));
  }

  private void validar(Aeroporto a) {
    if (a.getNomeAeroporto() == null || a.getNomeAeroporto().trim().isEmpty()) throw new IllegalArgumentException("nome_aeroporto obrigatório");
    if (a.getCidade() == null || a.getCidade().trim().isEmpty()) throw new IllegalArgumentException("cidade obrigatória");
    if (a.getCodigoIata() == null || a.getCodigoIata().length() != 3) throw new IllegalArgumentException("codigo_iata inválido");
    if (a.getAltitude() != null && a.getAltitude() < 0) throw new IllegalArgumentException("altitude não pode ser negativa");
  }
}

