package com.synerset.lottery.infrastructure.exceptionhandlers;

import com.synerset.lottery.infrastructure.exceptions.numberreceiver.ApiInvalidNumbersException;
import com.synerset.lottery.infrastructure.exceptions.objectmappers.JsonProcessingFailureException;
import com.synerset.lottery.infrastructure.exceptions.resultsannouncer.ApiInvalidUuidRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
class ExceptionGlobalHandler {

    @ExceptionHandler(ApiInvalidUuidRequestException.class)
    private ResponseEntity<ExceptionResponsePayload> handleIncorrectUuid(ApiInvalidUuidRequestException apiException) {
        return createErrorResponse(HttpStatus.BAD_REQUEST, apiException);
    }

    @ExceptionHandler(ApiInvalidNumbersException.class)
    private ResponseEntity<ExceptionResponsePayload> handleIncorrectNumberInput(ApiInvalidNumbersException apiException) {
        return createErrorResponse(HttpStatus.BAD_REQUEST, apiException);
    }

    @ExceptionHandler(JsonProcessingFailureException.class)
    private ResponseEntity<ExceptionResponsePayload> handleIncorrectNumberInput(JsonProcessingFailureException jsonException) {
        return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, jsonException);
    }

    private static ResponseEntity<ExceptionResponsePayload> createErrorResponse(HttpStatus status, RuntimeException ex) {
        String timestamp = LocalDateTime.now().toString();
        ExceptionResponsePayload payload = new ExceptionResponsePayload(status, timestamp, ex.getMessage(), ex.getClass());
        return ResponseEntity.status(status).body(payload);
    }


}
