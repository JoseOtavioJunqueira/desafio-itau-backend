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

    // Pega o valor do application.properties ou usa 60 como padrão
    @Value("${estatistica.intervalo-segundos:60}")
    private long intervaloSegundos;

    public EstatisticaResponse calcularEstatisticas() {
        // Pega a hora exata de agora
        OffsetDateTime agora = OffsetDateTime.now();
        // Calcula qual era o horário 60 segundos atrás (nosso limite de tempo)
        OffsetDateTime limite = agora.minusSeconds(intervaloSegundos);

        // 1. Busca TODAS as transações que estão na memória.
        // 2. Usa 'stream()' para processar a lista de forma moderna.
        // 3. 'filter()' remove todas as transações que são mais antigas que o nosso 'limite'.
        // 4. 'map()' transforma a lista de transações em uma lista apenas com os seus valores.
        // 5. 'collect()' junta tudo em uma nova lista chamada 'valoresUltimoMinuto'.
        List<BigDecimal> valoresUltimoMinuto = transacaoRepository.buscarTodas().stream()
                .filter(transacao -> transacao.dataHora().isAfter(limite) && !transacao.dataHora().isAfter(agora))
                .map(TransacaoRequest::valor)
                .collect(Collectors.toList());

        // Se a lista estiver vazia, retorna um objeto com tudo zerado, como pede o desafio.
        if (valoresUltimoMinuto.isEmpty()) {
            return new EstatisticaResponse(0L, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
        }

        // Se tiver transações, calcula as estatísticas
        long count = valoresUltimoMinuto.size();
        BigDecimal sum = valoresUltimoMinuto.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        // Divide a soma pela quantidade, com 2 casas decimais e arredondamento padrão
        BigDecimal avg = sum.divide(BigDecimal.valueOf(count), 2, RoundingMode.HALF_UP);
        BigDecimal min = valoresUltimoMinuto.stream().min(BigDecimal::compareTo).orElse(BigDecimal.ZERO);
        BigDecimal max = valoresUltimoMinuto.stream().max(BigDecimal::compareTo).orElse(BigDecimal.ZERO);

        // Retorna o objeto de resposta com todos os valores calculados
        return new EstatisticaResponse(count, sum, avg, min, max);
    }
}