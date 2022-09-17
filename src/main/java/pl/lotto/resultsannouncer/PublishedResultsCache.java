package pl.lotto.resultsannouncer;

import pl.lotto.resultsannouncer.dto.PublishedResultsDto;

import java.util.Optional;
import java.util.UUID;

interface PublishedResultsCache {

    PublishedResultsDto save(PublishedResultsDto lotteryResults);

    Optional<PublishedResultsDto> getCachedDtoForUuid(UUID uuid);

}
