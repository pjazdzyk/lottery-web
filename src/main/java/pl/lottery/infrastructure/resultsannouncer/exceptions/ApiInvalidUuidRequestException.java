package pl.lottery.infrastructure.resultsannouncer.exceptions;

public class ApiInvalidUuidRequestException extends RuntimeException {
    public ApiInvalidUuidRequestException(String message) {
        super(message);
    }
}
