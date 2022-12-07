package pl.lottery.resultsannouncer;

import pl.lottery.resultsannouncer.dto.AnnouncerResponseDto;

import java.util.UUID;

public interface ResultsAnnouncerFacade {

    AnnouncerResponseDto findResultsForId(UUID uuid);

    boolean isInvalidUuid(String uuid);
}
