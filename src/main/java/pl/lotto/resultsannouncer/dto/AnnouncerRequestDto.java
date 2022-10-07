package pl.lotto.resultsannouncer.dto;

import java.util.UUID;

public class AnnouncerRequestDto {

    private final UUID requestUuid;

    public AnnouncerRequestDto(UUID formUuid) {
        this.requestUuid = formUuid;
    }

    public UUID getRequestUuid() {
        return requestUuid;
    }
}
