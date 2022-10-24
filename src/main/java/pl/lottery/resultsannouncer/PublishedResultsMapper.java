package pl.lottery.resultsannouncer;

import pl.lottery.resultsannouncer.dto.AnnouncerStatus;
import pl.lottery.resultsannouncer.dto.AnnouncerResponseDto;
import pl.lottery.resultschecker.dto.CheckerDto;

import java.util.List;

class PublishedResultsMapper {

    public static AnnouncerResponseDto checkerDtoToPublishedDto(CheckerDto checkerDto, AnnouncerStatus status) {
        return new AnnouncerResponseDto(checkerDto.uuid(),
                checkerDto.drawDate(),
                List.copyOf(checkerDto.typedNumbers()),
                List.copyOf(checkerDto.winningNumbers()),
                List.copyOf(checkerDto.matchedNumbers()),
                checkerDto.isWinner(),
                status);
    }
}