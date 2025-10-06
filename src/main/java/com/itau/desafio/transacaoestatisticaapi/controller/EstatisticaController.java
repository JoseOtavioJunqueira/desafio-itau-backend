// src/main/java/com/itau/desafio/transacaoestatisticaapi/controller/EstatisticaController.java
package com.itau.desafio.transacaoestatisticaapi.controller;

import com.itau.desafio.transacaoestatisticaapi.dto.EstatisticaResponse;
import com.itau.desafio.transacaoestatisticaapi.service.EstatisticaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/estatistica")
@RequiredArgsConstructor
public class EstatisticaController {

    private final EstatisticaService estatisticaService;

    @GetMapping
    public ResponseEntity<EstatisticaResponse> getEstatisticas() {
        return ResponseEntity.ok(estatisticaService.calcularEstatisticas());
    }
}