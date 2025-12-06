package com.airportmanagement.adapters.in.web.controller;

import com.airportmanagement.adapters.in.web.dto.AeroportoResponse;
import com.airportmanagement.application.port.in.ConsultarAeroportosPort;
import com.airportmanagement.domain.model.Aeroporto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/aeroportos")
public class AeroportoController {
  private final ConsultarAeroportosPort service;

  public AeroportoController(ConsultarAeroportosPort service) {
    this.service = service;
  }

  @GetMapping
  public ResponseEntity<List<AeroportoResponse>> listar() {
    List<AeroportoResponse> body = service.listarTodos().stream().map(this::toResponse).collect(Collectors.toList());
    return ResponseEntity.ok(body);
  }

  @GetMapping("/{iata}")
  public ResponseEntity<AeroportoResponse> porIata(@PathVariable String iata) {
    Aeroporto a = service.porCodigoIata(iata);
    return ResponseEntity.ok(toResponse(a));
  }

  private AeroportoResponse toResponse(Aeroporto a) {
    AeroportoResponse r = new AeroportoResponse();
    r.idAeroporto = a.getIdAeroporto();
    r.nomeAeroporto = a.getNomeAeroporto();
    r.codigoIata = a.getCodigoIata();
    r.cidade = a.getCidade();
    r.codigoPaisIso = a.getCodigoPaisIso();
    r.latitude = a.getLatitude();
    r.longitude = a.getLongitude();
    r.altitude = a.getAltitude();
    return r;
  }
}

