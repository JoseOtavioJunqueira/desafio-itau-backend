package com.itau.desafio.transacaoestatisticaapi.service;

import com.itau.desafio.transacaoestatisticaapi.dto.EstatisticaResponse;
import com.itau.desafio.transacaoestatisticaapi.dto.TransacaoRequest;
import com.itau.desafio.transacaoestatisticaapi.repository.TransacaoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EstatisticaServiceTest {

    @Mock
    private TransacaoRepository transacaoRepository;

    @InjectMocks
    private EstatisticaService estatisticaService;

    private static final long INTERVALO_SEGUNDOS = 60L;

    @Test
    void deveRetornarEstatisticasZeradasQuandoNaoHaTransacoes() {
        ReflectionTestUtils.setField(estatisticaService, "intervaloSegundos", INTERVALO_SEGUNDOS);
        when(transacaoRepository.buscarTodas()).thenReturn(List.of());

        EstatisticaResponse resposta = estatisticaService.calcularEstatisticas();

        assertThat(resposta.count()).isZero();
        assertThat(resposta.sum()).isEqualByComparingTo(BigDecimal.ZERO);
        assertThat(resposta.avg()).isEqualByComparingTo(BigDecimal.ZERO);
        assertThat(resposta.min()).isEqualByComparingTo(BigDecimal.ZERO);
        assertThat(resposta.max()).isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Test
    void deveCalcularEstatisticasCorretamenteParaTransacoesDentroDaJanela() {
        ReflectionTestUtils.setField(estatisticaService, "intervaloSegundos", INTERVALO_SEGUNDOS);
        OffsetDateTime agora = OffsetDateTime.now();

        List<TransacaoRequest> transacoes = List.of(
                new TransacaoRequest(new BigDecimal("10.00"), agora.minusSeconds(5)),
                new TransacaoRequest(new BigDecimal("20.00"), agora.minusSeconds(10)),
                new TransacaoRequest(new BigDecimal("30.00"), agora.minusSeconds(15))
        );
        when(transacaoRepository.buscarTodas()).thenReturn(transacoes);

        EstatisticaResponse resposta = estatisticaService.calcularEstatisticas();

        assertThat(resposta.count()).isEqualTo(3L);
        assertThat(resposta.sum()).isEqualByComparingTo(new BigDecimal("60.00"));
        assertThat(resposta.avg()).isEqualByComparingTo(new BigDecimal("20.00"));
        assertThat(resposta.min()).isEqualByComparingTo(new BigDecimal("10.00"));
        assertThat(resposta.max()).isEqualByComparingTo(new BigDecimal("30.00"));
    }

    @Test
    void deveIgnorarTransacoesForaDaJanelaDeTempo() {
        ReflectionTestUtils.setField(estatisticaService, "intervaloSegundos", INTERVALO_SEGUNDOS);
        OffsetDateTime agora = OffsetDateTime.now();

        List<TransacaoRequest> transacoes = List.of(
                new TransacaoRequest(new BigDecimal("10.00"), agora.minusSeconds(5)),
                new TransacaoRequest(new BigDecimal("999.00"), agora.minusSeconds(120))
        );
        when(transacaoRepository.buscarTodas()).thenReturn(transacoes);

        EstatisticaResponse resposta = estatisticaService.calcularEstatisticas();

        assertThat(resposta.count()).isEqualTo(1L);
        assertThat(resposta.sum()).isEqualByComparingTo(new BigDecimal("10.00"));
    }
}
