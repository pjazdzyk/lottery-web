package com.synerset.lottery.resultsannouncer.dto;

import java.util.UUID;

public class AnnouncerRequestDto {

    private UUID requestUuid;

    public AnnouncerRequestDto() {
    }

    public AnnouncerRequestDto(UUID formUuid) {
        this.requestUuid = formUuid;
    }

    public UUID getRequestUuid() {
        return requestUuid;
    }

    public void setRequestUuid(UUID requestUuid) {
        this.requestUuid = requestUuid;
    }
}
