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
        return createBadRequestResponse(apiException.getMessage());
    }

    @ExceptionHandler(ApiInvalidNumbersException.class)
    private ResponseEntity<ExceptionResponsePayload> handleIncorrectNumberInput(ApiInvalidNumbersException apiException) {
        return createBadRequestResponse(apiException.getMessage());
    }

    @ExceptionHandler(JsonProcessingFailureException.class)
    private ResponseEntity<ExceptionResponsePayload> handleIncorrectNumberInput(JsonProcessingFailureException jsonException) {
        return createBadRequestResponse(jsonException.getMessage());
    }

    private static ResponseEntity<ExceptionResponsePayload> createBadRequestResponse(String exceptionMsg) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String timestamp = LocalDateTime.now().toString();
        ExceptionResponsePayload payload = new ExceptionResponsePayload(exceptionMsg, status, timestamp);
        return ResponseEntity.status(status).body(payload);
    }


}
