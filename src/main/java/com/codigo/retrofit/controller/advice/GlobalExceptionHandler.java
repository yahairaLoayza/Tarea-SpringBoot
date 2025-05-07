package com.codigo.retrofit.controller.advice;

import com.codigo.retrofit.aggregates.response.ApiErrorResponse;
import com.codigo.retrofit.exception.ConsultaReniecException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ApiErrorResponse> handleAnyException(Throwable ex) {
        ApiErrorResponse response = new ApiErrorResponse(ex.getClass().getSimpleName(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);

    }

    @ExceptionHandler(ConsultaReniecException.class)
    public ResponseEntity<ApiErrorResponse> handleReniecException(ConsultaReniecException ex) {
        ApiErrorResponse response = new ApiErrorResponse("RENIEC_SERVICE_ERROR", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(response);
    }
}
