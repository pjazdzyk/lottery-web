package com.synerset.lottery.infrastructure.exceptions.numberreceiver;

public class ApiInvalidNumbersException extends RuntimeException{

    public ApiInvalidNumbersException(String message) {
        super(message);
    }
}
