package pl.lotto.resultsannouncer;

import pl.lotto.resultsannouncer.dto.PublishedResultsDto;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

class CacheRepositoryStub implements PublishedResultsCache {

    private final Map<UUID, PublishedResultsDto> cacheInMemory = new ConcurrentHashMap<>();

    @Override
    public PublishedResultsDto save(PublishedResultsDto lotteryResults) {
        cacheInMemory.put(lotteryResults.uuid(), lotteryResults);
        return lotteryResults;
    }

    @Override
    public Optional<PublishedResultsDto> getCachedDtoForUuid(UUID uuid) {
        return Optional.ofNullable(cacheInMemory.get(uuid));
    }
}
