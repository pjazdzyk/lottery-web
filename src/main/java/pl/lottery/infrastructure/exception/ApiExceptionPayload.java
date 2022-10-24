package pl.lottery.infrastructure.exception;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public record ApiExceptionPayload(String message, HttpStatus status, String timeStamp) {
}
