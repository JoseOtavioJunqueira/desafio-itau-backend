package com.itau.desafio.transacaoestatisticaapi.service;

import com.itau.desafio.transacaoestatisticaapi.dto.TransacaoRequest;
import com.itau.desafio.transacaoestatisticaapi.repository.TransacaoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class TransacaoServiceTest {

    @Mock
    private TransacaoRepository transacaoRepository;

    @InjectMocks
    private TransacaoService transacaoService;

    @Test
    void deveDelegarRegistroDeTransacaoParaORepositorio() {
        TransacaoRequest transacao = new TransacaoRequest(new BigDecimal("42.50"), OffsetDateTime.now());

        transacaoService.registrarTransacao(transacao);

        verify(transacaoRepository, times(1)).salvar(transacao);
    }

    @Test
    void deveDelegarLimpezaDeTransacoesParaORepositorio() {
        transacaoService.deletarTransacoes();

        verify(transacaoRepository, times(1)).limpar();
    }
}
