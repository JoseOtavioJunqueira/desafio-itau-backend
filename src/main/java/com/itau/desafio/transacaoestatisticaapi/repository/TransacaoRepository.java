// src/main/java/com/itau/desafio/transacaoestatisticaapi/repository/TransacaoRepository.java
package com.itau.desafio.transacaoestatisticaapi.repository;

import com.itau.desafio.transacaoestatisticaapi.dto.TransacaoRequest;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository
public class TransacaoRepository {

    // usando uma estrutura thread-safe, ideal para cenários de alta leitura e baixa escrita/remoção.
    private final List<TransacaoRequest> transacoes = new CopyOnWriteArrayList<>();

    public void salvar(TransacaoRequest transacao) {
        this.transacoes.add(transacao);
    }

    public List<TransacaoRequest> buscarTodas() {
        return List.copyOf(transacoes); //vai retornar uma cópia imutável por segurança
    }

    public void limpar() {
        this.transacoes.clear();
    }
}