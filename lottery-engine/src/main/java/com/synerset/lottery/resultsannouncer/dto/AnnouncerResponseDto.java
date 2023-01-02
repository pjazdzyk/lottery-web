package com.synerset.lottery.resultsannouncer.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record AnnouncerResponseDto(UUID uuid,
                                   LocalDateTime drawDate,
                                   List<Integer> typedNumbers,
                                   List<Integer> winningNumbers,
                                   List<Integer> matchedNumbers,
                                   boolean isWinner,
                                   AnnouncerStatus status) implements Serializable {

    public static AnnouncerResponseDto notFoundDto() {
        return new AnnouncerResponseDto(null, null, null, null, null, false, AnnouncerStatus.NOT_FOUND);
    }

}
