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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TransacaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void devePersistirTransacaoValidaERetornar201() throws Exception {
        Map<String, Object> body = Map.of(
                "valor", new BigDecimal("100.00"),
                "dataHora", OffsetDateTime.now().toString()
        );

        mockMvc.perform(post("/transacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isCreated());
    }

    @Test
    void deveRetornar422ParaValorNegativo() throws Exception {
        Map<String, Object> body = Map.of(
                "valor", new BigDecimal("-10.00"),
                "dataHora", OffsetDateTime.now().toString()
        );

        mockMvc.perform(post("/transacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void deveRetornar422ParaDataNoFuturo() throws Exception {
        Map<String, Object> body = Map.of(
                "valor", new BigDecimal("10.00"),
                "dataHora", OffsetDateTime.now().plusDays(1).toString()
        );

        mockMvc.perform(post("/transacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void deveRetornar400ParaJsonMalformado() throws Exception {
        mockMvc.perform(post("/transacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"valor\": \"nao-e-um-numero\" }"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveLimparTransacoesERetornar200() throws Exception {
        mockMvc.perform(delete("/transacao"))
                .andExpect(status().isOk());
    }
}
