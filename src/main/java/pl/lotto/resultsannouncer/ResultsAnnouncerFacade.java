package pl.lotto.resultsannouncer;

import pl.lotto.resultsannouncer.dto.AnnouncerStatus;
import pl.lotto.resultsannouncer.dto.PublishedResultsDto;
import pl.lotto.resultschecker.ResultsCheckerFacade;
import pl.lotto.resultschecker.dto.CheckerDto;
import pl.lotto.resultschecker.dto.CheckerStatus;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class ResultsAnnouncerFacade {

    ResultsCheckerFacade resultsCheckerFacade;
    PublishedResultsCache publishedResultsCache;

    public ResultsAnnouncerFacade(ResultsCheckerFacade resultsCheckerFacade, PublishedResultsCache publishedResultsCache) {
        this.resultsCheckerFacade = resultsCheckerFacade;
        this.publishedResultsCache = publishedResultsCache;
    }

    public PublishedResultsDto getResultsForId(UUID uuid) {
        if (Objects.isNull(uuid)) {
            return notFoundDto();
        }
        Optional<PublishedResultsDto> publishedResultOptional = publishedResultsCache.getCachedDtoForUuid(uuid);
        if (publishedResultOptional.isPresent()) {
            return publishedResultOptional.get();
        }
        CheckerDto resultsForId = resultsCheckerFacade.getResultsForId(uuid);
        if (resultsForId.status() == CheckerStatus.NOT_FOUND) {
            return notFoundDto();
        }
        PublishedResultsDto publishedResultsDto = PublishedResultsMapper.checkerDtoToPublishedDto(resultsForId, AnnouncerStatus.PUBLISHED);
        publishedResultsCache.save(cachedDto(publishedResultsDto));
        return publishedResultsDto;
    }

    private PublishedResultsDto notFoundDto() {
        return new PublishedResultsDto(null, null, null, null, null, false, AnnouncerStatus.NOT_FOUND);
    }

    private PublishedResultsDto cachedDto(PublishedResultsDto publishedDto) {
        return new PublishedResultsDto(publishedDto.uuid(), publishedDto.drawDate(), publishedDto.typedNumbers(),
                publishedDto.winningNumbers(), publishedDto.matchedNumbers(), publishedDto.isWinner(),
                AnnouncerStatus.CACHED);
    }

}
