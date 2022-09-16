package pl.lotto.resultsannouncer;

import pl.lotto.resultsannouncer.dto.AnnouncerStatus;
import pl.lotto.resultsannouncer.dto.PublishedResultsDto;
import pl.lotto.resultschecker.dto.CheckerDto;

class PublishedResultsMapper {

    public static PublishedResultsDto checkerDtoToPublishedDto(CheckerDto checkerDto, AnnouncerStatus status) {
        return new PublishedResultsDto(checkerDto.uuid(), checkerDto.drawDate(), checkerDto.typedNumbers(),
                checkerDto.winningNumbers(), checkerDto.matchedNumbers(), checkerDto.isWinner(), status);
    }
}