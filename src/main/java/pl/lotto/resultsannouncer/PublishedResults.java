package pl.lotto.resultsannouncer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public record PublishedResults(UUID uuid, LocalDateTime drawDate, List<Integer> winningNumbers,
                                  List<Integer> matchedNumbers, boolean isWinner) {

    @Override
    public List<Integer> winningNumbers() {
        return new ArrayList<>(winningNumbers);
    }

    public List<Integer> matchedNumbers() {
        return new ArrayList<>(winningNumbers);
    }
}
