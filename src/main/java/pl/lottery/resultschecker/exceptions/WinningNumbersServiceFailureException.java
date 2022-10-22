package pl.lottery.resultschecker.exceptions;

public class WinningNumbersServiceFailureException extends RuntimeException {
    public WinningNumbersServiceFailureException(String msg) {
        super(msg);
    }
}
