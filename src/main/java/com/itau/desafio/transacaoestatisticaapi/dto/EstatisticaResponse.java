// src/main/java/com/itau/desafio/transacaoestatisticaapi/dto/EstatisticaResponse.java
package com.itau.desafio.transacaoestatisticaapi.dto;

import java.math.BigDecimal;

public record EstatisticaResponse(
    long count,
    BigDecimal sum,
    BigDecimal avg,
    BigDecimal min,
    BigDecimal max
) {}