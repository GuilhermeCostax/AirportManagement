package com.airportmanagement.adapters.in.web.controller;

import com.airportmanagement.adapters.in.web.dto.AeroportoResponse;
import com.airportmanagement.adapters.in.web.dto.AeroportoRequest;
import com.airportmanagement.application.port.in.ConsultarAeroportosPort;
import com.airportmanagement.application.port.in.CadastrarAeroportoPort;
import com.airportmanagement.domain.model.Aeroporto;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/aeroportos")
public class AeroportoController {
  private final ConsultarAeroportosPort service;
  private final CadastrarAeroportoPort cadastrarService;

  public AeroportoController(ConsultarAeroportosPort service, CadastrarAeroportoPort cadastrarService) {
    this.service = service;
    this.cadastrarService = cadastrarService;
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

  @PostMapping
  public ResponseEntity<AeroportoResponse> criar(@RequestBody AeroportoRequest body) {
    Aeroporto novo = new Aeroporto(null, body.nomeAeroporto, body.codigoIata, body.cidade, body.codigoPaisIso, body.latitude, body.longitude, body.altitude);
    Aeroporto criado = cadastrarService.cadastrar(novo);
    AeroportoResponse resp = toResponse(criado);
    HttpHeaders headers = new HttpHeaders();
    headers.add("Location", "/api/v1/aeroportos/" + resp.codigoIata);
    return new ResponseEntity<>(resp, headers, HttpStatus.CREATED);
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
