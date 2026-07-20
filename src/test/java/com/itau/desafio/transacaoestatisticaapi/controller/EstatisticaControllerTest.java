package com.itau.desafio.transacaoestatisticaapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class EstatisticaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveRetornarEstatisticasZeradasQuandoNaoHaTransacoes() throws Exception {
        mockMvc.perform(delete("/transacao"));

        mockMvc.perform(get("/estatistica"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(0));
    }

    @Test
    void deveRefletirTransacaoRecemCriadaNasEstatisticas() throws Exception {
        mockMvc.perform(delete("/transacao"));

        Map<String, Object> body = Map.of(
                "valor", new BigDecimal("50.00"),
                "dataHora", OffsetDateTime.now().toString()
        );
        mockMvc.perform(post("/transacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/estatistica"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(1))
                .andExpect(jsonPath("$.sum").value(50.00));
    }
}
