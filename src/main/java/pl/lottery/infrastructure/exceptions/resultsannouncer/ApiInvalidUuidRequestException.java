package pl.lottery.infrastructure.exceptions.resultsannouncer;

public class ApiInvalidUuidRequestException extends RuntimeException {
    public ApiInvalidUuidRequestException(String message) {
        super(message);
    }
}
