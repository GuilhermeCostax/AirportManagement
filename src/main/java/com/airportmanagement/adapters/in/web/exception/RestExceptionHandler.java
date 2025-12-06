package com.airportmanagement.adapters.in.web.exception;

import com.airportmanagement.shared.exception.AeroportoNaoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class RestExceptionHandler {
  @ExceptionHandler(AeroportoNaoEncontradoException.class)
  public ResponseEntity<Map<String, Object>> handleNotFound(AeroportoNaoEncontradoException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Aeroporto não encontrado", "iata", ex.getIata()));
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<Map<String, Object>> handleBadRequest(IllegalArgumentException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", ex.getMessage()));
  }
}

