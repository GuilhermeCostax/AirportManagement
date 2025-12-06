package com.airportmanagement.domain;

import java.util.Map;

public final class Paises {
  private static final Map<String, String> MAP = Map.of(
    "Brazil", "BR",
    "United States", "US",
    "Portugal", "PT"
  );

  public static String obterIsoPais(String nome) {
    if (nome == null) return null;
    String key = nome.trim();
    String v = MAP.get(key);
    return v;
  }
}

