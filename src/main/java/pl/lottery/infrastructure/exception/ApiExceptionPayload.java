package pl.lottery.infrastructure.exception;

import org.springframework.http.HttpStatus;

record ApiExceptionPayload(String message, HttpStatus status, String timeStamp) {
}
