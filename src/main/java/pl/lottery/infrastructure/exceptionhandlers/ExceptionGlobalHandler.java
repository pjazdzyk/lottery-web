package pl.lottery.infrastructure.exceptionhandlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.lottery.infrastructure.exceptions.numberreceiver.ApiInvalidNumbersException;
import pl.lottery.infrastructure.exceptions.objectmappers.JsonProcessingFailureException;
import pl.lottery.infrastructure.exceptions.resultsannouncer.ApiInvalidUuidRequestException;

import java.time.LocalDateTime;

@ControllerAdvice
class ExceptionGlobalHandler {

    @ExceptionHandler(ApiInvalidUuidRequestException.class)
    private ResponseEntity<ExceptionResponsePayload> handleIncorrectUuid(ApiInvalidUuidRequestException apiException) {
        return createBadRequestResponse(apiException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ApiInvalidNumbersException.class)
    private ResponseEntity<ExceptionResponsePayload> handleIncorrectNumberInput(ApiInvalidNumbersException apiException) {
        return createBadRequestResponse(apiException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(JsonProcessingFailureException.class)
    private ResponseEntity<ExceptionResponsePayload> handleIncorrectNumberInput(JsonProcessingFailureException jsonException) {
        return createBadRequestResponse(jsonException.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private static ResponseEntity<ExceptionResponsePayload> createBadRequestResponse(String exceptionMsg, HttpStatus status) {
        String timestamp = LocalDateTime.now().toString();
        ExceptionResponsePayload payload = new ExceptionResponsePayload(exceptionMsg, status, timestamp);
        return ResponseEntity.status(status).body(payload);
    }


}
