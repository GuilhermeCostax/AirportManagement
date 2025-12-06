package com.airportmanagement.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ConversoesTest {
  @Test
  void deveConverterPesParaMetros() {
    double m = Conversoes.converterPesParaMetros(1000);
    Assertions.assertEquals(304.8, m, 0.0001);
  }
}

