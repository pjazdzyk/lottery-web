package pl.lottery.infrastructure.exceptionhandlers;

import org.springframework.http.HttpStatus;

record ExceptionResponsePayload(HttpStatus status, String timeStamp, String message, Class<? extends RuntimeException> exceptionType) {
}
