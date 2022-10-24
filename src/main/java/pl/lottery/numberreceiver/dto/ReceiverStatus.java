package pl.lottery.numberreceiver.dto;

public enum ReceiverStatus {

    SAVED("Saved in database."),
    DELETED("Deleted from database"),
    NOT_FOUND("Coupon not found");
    ReceiverStatus(String message) {
    }

}
