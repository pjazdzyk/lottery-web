package com.synerset.lottery.resultsannouncer;

import com.synerset.lottery.resultsannouncer.dto.AnnouncerResponseDto;
import com.synerset.lottery.resultsannouncer.dto.AnnouncerStatus;
import com.synerset.lottery.resultschecker.dto.CheckerDto;

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