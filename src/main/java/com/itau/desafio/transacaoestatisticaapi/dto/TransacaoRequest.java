// src/main/java/com/itau/desafio/transacaoestatisticaapi/dto/TransacaoRequest.java
package com.itau.desafio.transacaoestatisticaapi.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record TransacaoRequest(
    @NotNull(message = "O campo 'valor' é obrigatório.")
    @PositiveOrZero(message = "O valor da transação não pode ser negativo.")
    BigDecimal valor,

    @NotNull(message = "O campo 'dataHora' é obrigatório.")
    @PastOrPresent(message = "A data da transação não pode ser no futuro.")
    OffsetDateTime dataHora
) {}