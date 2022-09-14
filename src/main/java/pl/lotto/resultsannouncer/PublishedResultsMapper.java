package pl.lotto.resultsannouncer;

import pl.lotto.resultsannouncer.dto.PublishedResultsDto;

import java.util.List;
import java.util.stream.Collectors;

class PublishedResultsMapper {

    public static PublishedResults toPublishedResults(PublishedResultsDto PublishedResultsDto) {
        return new PublishedResults(PublishedResultsDto.uuid(), PublishedResultsDto.drawDate(), PublishedResultsDto.winningNumbers(),
                PublishedResultsDto.matchedNumbers(), PublishedResultsDto.isWinner());
    }

    public static PublishedResultsDto toDto(PublishedResults PublishedResults) {
        return new PublishedResultsDto(PublishedResults.uuid(), PublishedResults.drawDate(), PublishedResults.winningNumbers(),
                PublishedResults.matchedNumbers(), PublishedResults.isWinner());
    }

    static List<PublishedResultsDto> toDtoList(List<PublishedResults> PublishedResults) {
        return PublishedResults.stream()
                .map(PublishedResultsMapper::toDto)
                .collect(Collectors.toList());
    }

    static List<PublishedResults> toPublishedResultsList(List<PublishedResultsDto> couponDto) {
        return couponDto.stream()
                .map(PublishedResultsMapper::toPublishedResults)
                .collect(Collectors.toList());
    }

}
