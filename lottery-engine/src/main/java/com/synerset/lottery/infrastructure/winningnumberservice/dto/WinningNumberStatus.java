package com.synerset.lottery.infrastructure.winningnumberservice.dto;

public enum WinningNumberStatus {
    GENERATED,
    LOADED_FROM_DB,
    DELETED,
    NOT_FOUND,
    ALREADY_EXISTS
}
