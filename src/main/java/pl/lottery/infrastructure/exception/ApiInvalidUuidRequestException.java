package pl.lottery.infrastructure.exception;

public class ApiInvalidUuidRequestException extends RuntimeException{
    public ApiInvalidUuidRequestException(String message) {
        super(message);
    }
}
