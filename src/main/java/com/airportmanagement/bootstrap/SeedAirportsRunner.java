package com.airportmanagement.bootstrap;

import com.airportmanagement.application.port.out.AeroportoRepository;
import com.airportmanagement.domain.Conversoes;
import com.airportmanagement.domain.Paises;
import com.airportmanagement.domain.model.Aeroporto;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Profile("seed")
@Component
public class SeedAirportsRunner implements CommandLineRunner {
  private final AeroportoRepository repository;
  public SeedAirportsRunner(AeroportoRepository repository) {
    this.repository = repository;
  }

  @Override
  public void run(String... args) throws Exception {
    if (!repository.findAll().isEmpty()) return;
    URL url = new URL("https://raw.githubusercontent.com/profdiegoaugusto/banco-dados/master/mysql/linguagem-consulta-dados/data/airports.csv");
    try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8))) {
      String line;
      boolean first = true;
      while ((line = br.readLine()) != null) {
        if (first) { first = false; continue; }
        List<String> cols = parseCsvLine(line);
        if (cols.size() < 9) continue;
        String nome = cols.get(1);
        String cidade = cols.get(2);
        String pais = cols.get(3);
        String iata = cols.get(4);
        String latStr = cols.get(6);
        String lonStr = cols.get(7);
        String altFeetStr = cols.get(8);
        if (iata == null || iata.isBlank() || iata.length() != 3) continue;
        if (nome == null || nome.isBlank() || cidade == null || cidade.isBlank()) continue;
        Double lat = parseDouble(latStr);
        Double lon = parseDouble(lonStr);
        Double altFeet = parseDouble(altFeetStr);
        Double altMeters = altFeet != null ? Conversoes.converterPesParaMetros(altFeet) : null;
        String iso = Paises.obterIsoPais(pais);
        Aeroporto a = new Aeroporto(null, nome, iata.toUpperCase(), cidade, iso, lat, lon, altMeters);
        repository.save(a);
      }
    }
  }

  private static Double parseDouble(String s) {
    try { return s == null || s.isBlank() ? null : Double.parseDouble(s); } catch (Exception e) { return null; }
  }

  private static List<String> parseCsvLine(String line) {
    List<String> out = new ArrayList<>();
    StringBuilder sb = new StringBuilder();
    boolean inQuotes = false;
    for (int i = 0; i < line.length(); i++) {
      char c = line.charAt(i);
      if (c == '"') { inQuotes = !inQuotes; continue; }
      if (c == ',' && !inQuotes) { out.add(sb.toString()); sb.setLength(0); continue; }
      sb.append(c);
    }
    out.add(sb.toString());
    return out;
  }
}

