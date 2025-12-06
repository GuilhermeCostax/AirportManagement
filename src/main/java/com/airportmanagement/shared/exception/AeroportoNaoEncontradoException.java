package com.airportmanagement.shared.exception;

public class AeroportoNaoEncontradoException extends RuntimeException {
  private final String iata;
  public AeroportoNaoEncontradoException(String iata) {
    this.iata = iata;
  }
  public String getIata() { return iata; }
}

