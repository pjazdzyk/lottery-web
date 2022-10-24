package pl.lottery.infrastructure.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ApiHttpExceptionHandler {

    @ExceptionHandler(ApiInvalidUuidRequestException.class)
    private ResponseEntity<ApiExceptionPayload> handleIncorrectUuid(ApiInvalidUuidRequestException apiUuidException) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ApiExceptionPayload payload = new ApiExceptionPayload(apiUuidException.getMessage(),status, LocalDateTime.now().toString());
        return ResponseEntity.status(status).body(payload);
    }

}
