package com.synerset.lottery.resultsannouncer;

import com.synerset.lottery.resultsannouncer.dto.AnnouncerResponseDto;

import java.util.UUID;

public interface ResultsAnnouncerFacade {

    AnnouncerResponseDto findResultsForId(UUID uuid);

    boolean isInvalidUuid(String uuid);
}
