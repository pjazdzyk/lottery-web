package pl.lotto.resultsannouncer.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record PublishedResultsDto(UUID uuid,
                                  LocalDateTime drawDate,
                                  List<Integer> typedNumbers,
                                  List<Integer> winningNumbers,
                                  List<Integer> matchedNumbers,
                                  boolean isWinner,
                                  AnnouncerStatus status) {
}
