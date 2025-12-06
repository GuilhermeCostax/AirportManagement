package com.airportmanagement.adapters.in.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
}

