package pl.lotto.resultsannouncer;

import org.springframework.cache.annotation.Cacheable;
import pl.lotto.resultsannouncer.dto.AnnouncerStatus;
import pl.lotto.resultsannouncer.dto.AnnouncerResponseDto;
import pl.lotto.resultschecker.ResultsCheckerFacade;
import pl.lotto.resultschecker.dto.CheckerDto;
import pl.lotto.resultschecker.dto.CheckerStatus;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class ResultsAnnouncerFacade {

    ResultsCheckerFacade resultsCheckerFacade;

    public ResultsAnnouncerFacade(ResultsCheckerFacade resultsCheckerFacade) {
        this.resultsCheckerFacade = resultsCheckerFacade;
    }

    @Cacheable("publishedResultsDtoCache")
    public AnnouncerResponseDto getResultsForId(UUID uuid) {
        if (Objects.isNull(uuid)) {
            return notFoundDto();
        }

        CheckerDto resultsForId = resultsCheckerFacade.getResultsForId(uuid);

        if (resultsForId.status() == CheckerStatus.NOT_FOUND) {
            return notFoundDto();
        }

        return PublishedResultsMapper.checkerDtoToPublishedDto(resultsForId, AnnouncerStatus.PUBLISHED);
    }

    private AnnouncerResponseDto notFoundDto() {
        return new AnnouncerResponseDto(null, null, null, null, null, false, AnnouncerStatus.NOT_FOUND);
    }

}
