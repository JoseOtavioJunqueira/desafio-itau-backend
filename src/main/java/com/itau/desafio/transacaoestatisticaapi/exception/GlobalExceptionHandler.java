// src/main/java/com/itau/desafio/transacaoestatisticaapi/exception/GlobalExceptionHandler.java
package com.itau.desafio.transacaoestatisticaapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        // Quando uma validação falha, o status correto é 422
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
    }
    
    @ExceptionHandler(com.fasterxml.jackson.databind.exc.InvalidFormatException.class)
    public ResponseEntity<Object> handleInvalidFormat(com.fasterxml.jackson.databind.exc.InvalidFormatException ex) {
        // Quando o formato do JSON está incorreto (ex: texto no lugar de número)
        return ResponseEntity.badRequest().build();
    }
}