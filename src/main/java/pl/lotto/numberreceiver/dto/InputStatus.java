package pl.lotto.numberreceiver.dto;

public enum InputStatus {

    SAVED("Saved in database."),
    DELETED("Deleted from database"),
    NOT_FOUND("Coupon not found"),
    VALIDATED("Validated by game rules"),
    INVALID("Invalid numbers");

    String info;

    InputStatus(String message) {
        this.info = message;
    }

    void setInfo(String info) {
        this.info = info;
    }
    
    public static InputStatus getInvalidStatusWithMsg(String customMsg){
        InputStatus invalidStatus = InputStatus.INVALID;
        invalidStatus.setInfo(customMsg);
        return invalidStatus;
    }

}
