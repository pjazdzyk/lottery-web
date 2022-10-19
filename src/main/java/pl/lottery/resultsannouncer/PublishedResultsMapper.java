package pl.lottery.resultsannouncer;

import pl.lottery.resultsannouncer.dto.AnnouncerStatus;
import pl.lottery.resultsannouncer.dto.AnnouncerResponseDto;
import pl.lottery.resultschecker.dto.CheckerDto;

class PublishedResultsMapper {

    public static AnnouncerResponseDto checkerDtoToPublishedDto(CheckerDto checkerDto, AnnouncerStatus status) {
        return new AnnouncerResponseDto(checkerDto.uuid(), checkerDto.drawDate(), checkerDto.typedNumbers(),
                checkerDto.winningNumbers(), checkerDto.matchedNumbers(), checkerDto.isWinner(), status);
    }
}