package pl.lottery.infrastructure.numberreceiver.exceptions;

public class ApiInvalidNumbersException extends RuntimeException{

    public ApiInvalidNumbersException(String message) {
        super(message);
    }
}
