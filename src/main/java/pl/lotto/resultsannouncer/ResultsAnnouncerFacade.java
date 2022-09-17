package pl.lotto.resultsannouncer;

import pl.lotto.resultsannouncer.dto.AnnouncerStatus;
import pl.lotto.resultsannouncer.dto.PublishedResultsDto;
import pl.lotto.resultschecker.ResultsCheckerFacade;
import pl.lotto.resultschecker.dto.CheckerDto;
import pl.lotto.resultschecker.dto.CheckerStatus;

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
        Optional<PublishedResultsDto> publishedResultsForUuidOpt = publishedResultsCache.getCachedDtoForUuid(uuid);
        if (publishedResultsForUuidOpt.isPresent()) {
            return publishedResultsForUuidOpt.get();
        }
        CheckerDto resultsForId = resultsCheckerFacade.getResultsForId(uuid);
        if (resultsForId.status() == CheckerStatus.NOT_FOUND) {
            return notFoundDto();
        }
        PublishedResultsDto resultsToPublishDto = PublishedResultsMapper.checkerDtoToPublishedDto(resultsForId, AnnouncerStatus.PUBLISHED);
        return publishedResultsCache.save(resultsToPublishDto);
    }

    private PublishedResultsDto notFoundDto() {
        return new PublishedResultsDto(null, null, null, null, null, false, AnnouncerStatus.NOT_FOUND);
    }


}
