package com.synerset.lottery.infrastructure.exceptions.objectmappers;

public class JsonProcessingFailureException extends RuntimeException{

    public JsonProcessingFailureException(String message) {
        super(message);
    }
}
