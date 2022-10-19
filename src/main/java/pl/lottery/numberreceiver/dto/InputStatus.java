package pl.lottery.numberreceiver.dto;

public enum InputStatus {

    SAVED("Saved in database."),
    DELETED("Deleted from database"),
    NOT_FOUND("Coupon not found"),
    VALIDATED("Validated by game rules"),
    INVALID("Invalid numbers");

    InputStatus(String message) {
    }

}
