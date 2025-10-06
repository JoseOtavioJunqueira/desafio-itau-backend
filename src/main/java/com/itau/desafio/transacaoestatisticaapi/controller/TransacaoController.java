// src/main/java/com/itau/desafio/transacaoestatisticaapi/controller/TransacaoController.java
package com.itau.desafio.transacaoestatisticaapi.controller;

import com.itau.desafio.transacaoestatisticaapi.dto.TransacaoRequest;
import com.itau.desafio.transacaoestatisticaapi.service.TransacaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transacao")
@RequiredArgsConstructor
@Slf4j // Para o extra de Logs
public class TransacaoController {

    private final TransacaoService transacaoService;

    @PostMapping
    public ResponseEntity<Void> criarTransacao(@Valid @RequestBody TransacaoRequest transacaoRequest) {
        log.info("Recebida nova transação: {}", transacaoRequest);
        transacaoService.registrarTransacao(transacaoRequest);
        log.info("Transação registrada com sucesso.");
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deletarTransacoes() {
        log.info("Recebida requisição para deletar todas as transações.");
        transacaoService.deletarTransacoes();
        log.info("Todas as transações foram deletadas com sucesso.");
        return ResponseEntity.ok().build();
    }
}