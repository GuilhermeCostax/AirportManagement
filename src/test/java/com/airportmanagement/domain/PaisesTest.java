package com.airportmanagement.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PaisesTest {
  @Test
  void deveObterIsoBrasil() {
    String iso = Paises.obterIsoPais("Brazil");
    Assertions.assertEquals("BR", iso);
  }
}

