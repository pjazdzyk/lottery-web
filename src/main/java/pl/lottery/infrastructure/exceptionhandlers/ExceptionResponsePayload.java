package pl.lottery.infrastructure.exceptionhandlers;

import org.springframework.http.HttpStatus;

record ExceptionResponsePayload(String message, HttpStatus status, String timeStamp) {
}
