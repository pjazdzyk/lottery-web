package pl.lottery.infrastructure.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.lottery.infrastructure.numberreceiver.exceptions.ApiInvalidNumbersException;
import pl.lottery.infrastructure.resultsannouncer.exceptions.ApiInvalidUuidRequestException;

import java.time.LocalDateTime;

@ControllerAdvice
class ApiHttpExceptionHandler {

    @ExceptionHandler(ApiInvalidUuidRequestException.class)
    private ResponseEntity<ApiExceptionPayload> handleIncorrectUuid(ApiInvalidUuidRequestException apiException) {
        return createBadRequestResponse(apiException.getMessage());
    }

    @ExceptionHandler(ApiInvalidNumbersException.class)
    private ResponseEntity<ApiExceptionPayload> handleIncorrectNumberInput(ApiInvalidNumbersException apiException) {
        return createBadRequestResponse(apiException.getMessage());
    }

    private static ResponseEntity<ApiExceptionPayload> createBadRequestResponse(String exceptionMsg) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String timestamp = LocalDateTime.now().toString();
        ApiExceptionPayload payload = new ApiExceptionPayload(exceptionMsg, status, timestamp);
        return ResponseEntity.status(status).body(payload);
    }


}
