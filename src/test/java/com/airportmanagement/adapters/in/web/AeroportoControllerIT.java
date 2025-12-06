package com.airportmanagement.adapters.in.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AeroportoControllerIT {
  @Autowired
  private MockMvc mockMvc;

  @Test
  void deveBuscarPorIata() throws Exception {
    mockMvc.perform(get("/api/v1/aeroportos/GKA"))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.codigoIata").value("GKA"))
      .andExpect(jsonPath("$.nomeAeroporto").value("Goroka Airport"));
  }

  @Test
  void deveListarTodos() throws Exception {
    mockMvc.perform(get("/api/v1/aeroportos"))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$[0].codigoIata").exists());
  }

  @Test
  void deveCriarAeroporto() throws Exception {
    String json = "{\n" +
      "  \"nomeAeroporto\": \"Teste Airport\",\n" +
      "  \"codigoIata\": \"TST\",\n" +
      "  \"cidade\": \"Teste City\",\n" +
      "  \"codigoPaisIso\": null,\n" +
      "  \"latitude\": 1.23,\n" +
      "  \"longitude\": 4.56,\n" +
      "  \"altitude\": 100.0\n" +
      "}";

    mockMvc.perform(post("/api/v1/aeroportos").contentType("application/json").content(json))
      .andExpect(status().isCreated())
      .andExpect(header().string("Location", "/api/v1/aeroportos/TST"))
      .andExpect(jsonPath("$.codigoIata").value("TST"))
      .andExpect(jsonPath("$.nomeAeroporto").value("Teste Airport"));

    mockMvc.perform(get("/api/v1/aeroportos/TST"))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.codigoIata").value("TST"));
  }

  @Test
  void deveAtualizarAeroporto() throws Exception {
    String json = "{\n" +
      "  \"nomeAeroporto\": \"Madang Updated\",\n" +
      "  \"codigoIata\": \"MAG\",\n" +
      "  \"cidade\": \"Madang\",\n" +
      "  \"codigoPaisIso\": null,\n" +
      "  \"latitude\": -5.20707988739,\n" +
      "  \"longitude\": 145.789001465,\n" +
      "  \"altitude\": 42.0\n" +
      "}";

    mockMvc.perform(post("/api/v1/aeroportos/MAG").contentType("application/json").content(json))
      .andExpect(status().isMethodNotAllowed());

    mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put("/api/v1/aeroportos/MAG").contentType("application/json").content(json))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.nomeAeroporto").value("Madang Updated"))
      .andExpect(jsonPath("$.altitude").value(42.0));

    mockMvc.perform(get("/api/v1/aeroportos/MAG"))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.nomeAeroporto").value("Madang Updated"))
      .andExpect(jsonPath("$.altitude").value(42.0));
  }
}
