// src/main/java/com/itau/desafio/transacaoestatisticaapi/service/EstatisticaService.java

package com.itau.desafio.transacaoestatisticaapi.service;

import com.itau.desafio.transacaoestatisticaapi.dto.EstatisticaResponse;
import com.itau.desafio.transacaoestatisticaapi.dto.TransacaoRequest;
import com.itau.desafio.transacaoestatisticaapi.repository.TransacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EstatisticaService {

    private final TransacaoRepository transacaoRepository;

    //valor application.properties ou usa 60 como padrão
    @Value("${estatistica.intervalo-segundos:60}")
    private long intervaloSegundos;

    public EstatisticaResponse calcularEstatisticas() {
        //hora exata de agora
        OffsetDateTime agora = OffsetDateTime.now();
        //hora de agora - 60 seg
        OffsetDateTime limite = agora.minusSeconds(intervaloSegundos);

        //Busca TODAS as transações que estão na memória e remove as que são de mais de 60 seg atrás
      
        List<BigDecimal> valoresUltimoMinuto = transacaoRepository.buscarTodas().stream()
                .filter(transacao -> transacao.dataHora().isAfter(limite) && !transacao.dataHora().isAfter(agora))
                .map(TransacaoRequest::valor)
                .collect(Collectors.toList());

        //se vazio, retorna tudo zero
        if (valoresUltimoMinuto.isEmpty()) {
            return new EstatisticaResponse(0L, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
        }

        //se não for vazio, calcula:
        long count = valoresUltimoMinuto.size();
        BigDecimal sum = valoresUltimoMinuto.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal avg = sum.divide(BigDecimal.valueOf(count), 2, RoundingMode.HALF_UP);
        BigDecimal min = valoresUltimoMinuto.stream().min(BigDecimal::compareTo).orElse(BigDecimal.ZERO);
        BigDecimal max = valoresUltimoMinuto.stream().max(BigDecimal::compareTo).orElse(BigDecimal.ZERO);

        return new EstatisticaResponse(count, sum, avg, min, max);
    }
}