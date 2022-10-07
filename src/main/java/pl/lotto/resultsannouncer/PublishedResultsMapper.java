package pl.lotto.resultsannouncer;

import pl.lotto.resultsannouncer.dto.AnnouncerStatus;
import pl.lotto.resultsannouncer.dto.AnnouncerResponseDto;
import pl.lotto.resultschecker.dto.CheckerDto;

class PublishedResultsMapper {

    public static AnnouncerResponseDto checkerDtoToPublishedDto(CheckerDto checkerDto, AnnouncerStatus status) {
        return new AnnouncerResponseDto(checkerDto.uuid(), checkerDto.drawDate(), checkerDto.typedNumbers(),
                checkerDto.winningNumbers(), checkerDto.matchedNumbers(), checkerDto.isWinner(), status);
    }
}