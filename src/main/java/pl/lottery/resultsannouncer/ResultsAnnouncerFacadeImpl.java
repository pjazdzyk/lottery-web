package pl.lottery.resultsannouncer;

import org.springframework.cache.annotation.Cacheable;
import pl.lottery.resultsannouncer.dto.AnnouncerStatus;
import pl.lottery.resultsannouncer.dto.AnnouncerResponseDto;
import pl.lottery.resultschecker.ResultsCheckerFacade;
import pl.lottery.resultschecker.dto.CheckerDto;
import pl.lottery.resultschecker.dto.CheckerStatus;

import java.util.Objects;
import java.util.UUID;

class ResultsAnnouncerFacadeImpl implements ResultsAnnouncerFacade {

    private final ResultsCheckerFacade resultsCheckerFacade;

    public ResultsAnnouncerFacadeImpl(ResultsCheckerFacade resultsCheckerFacade) {
        this.resultsCheckerFacade = resultsCheckerFacade;
    }
    @Override
    @Cacheable("resultsForUuid")
    public AnnouncerResponseDto findResultsForId(UUID uuid) {
        if (Objects.isNull(uuid)) {
            return AnnouncerResponseDto.notFoundDto();
        }

        CheckerDto resultsForId = resultsCheckerFacade.findResultsForId(uuid);

        if (resultsForId.status() == CheckerStatus.NOT_FOUND) {
            return AnnouncerResponseDto.notFoundDto();
        }

        return PublishedResultsMapper.checkerDtoToPublishedDto(resultsForId, AnnouncerStatus.PUBLISHED);
    }

    @Override
    public boolean isInvalidUuid(String uuid){
        return !UuidValidator.isValidUUID(uuid);
    }

}
