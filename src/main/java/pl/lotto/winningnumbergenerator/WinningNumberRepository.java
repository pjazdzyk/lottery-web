package pl.lotto.winningnumbergenerator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

interface WinningNumberRepository {

    WinningNumbers save(WinningNumbers winningNumbers);

    Optional<WinningNumbers> getWinningNumbersForDrawDate(LocalDateTime drawDate);

    boolean containsNumbersOfDrawDate(LocalDateTime drawDate);

    Optional<WinningNumbers> deleteWinningNumbersForDate(LocalDateTime drawDate);

    List<WinningNumbers> getAllWinningNumbers();
}
