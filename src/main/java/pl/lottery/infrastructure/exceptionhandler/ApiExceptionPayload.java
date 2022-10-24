package pl.lottery.infrastructure.exceptionhandler;

import org.springframework.http.HttpStatus;

record ApiExceptionPayload(String message, HttpStatus status, String timeStamp) {
}
