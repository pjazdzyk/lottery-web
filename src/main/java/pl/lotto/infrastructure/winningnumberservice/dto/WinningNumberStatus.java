package pl.lotto.infrastructure.winningnumberservice.dto;

public enum WinningNumberStatus {
    GENERATED,
    LOADED_FROM_DB,
    DELETED,
    NOT_FOUND,
    ALREADY_EXISTS
}
