package pl.lottery.infrastructure.objectmappers.exceptions;

public class JsonProcessingFailureException extends RuntimeException{

    public JsonProcessingFailureException(String message) {
        super(message);
    }
}
