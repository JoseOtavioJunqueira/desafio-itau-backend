// src/main/java/com/itau/desafio/transacaoestatisticaapi/service/TransacaoService.java
package com.itau.desafio.transacaoestatisticaapi.service;

import com.itau.desafio.transacaoestatisticaapi.dto.TransacaoRequest;
import com.itau.desafio.transacaoestatisticaapi.repository.TransacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor // injeção de dependência
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;

    public void registrarTransacao(TransacaoRequest transacao) {
        transacaoRepository.salvar(transacao);
    }

    public void deletarTransacoes() {
    transacaoRepository.limpar();
}
}

