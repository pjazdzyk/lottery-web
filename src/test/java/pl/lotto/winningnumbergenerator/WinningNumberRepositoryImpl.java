package pl.lotto.winningnumbergenerator;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

class WinningNumberRepositoryImpl implements WinningNumberRepository {

    Map<LocalDateTime, WinningNumbers> databaseInMemory = new ConcurrentHashMap<>();


    @Override //TODO to discuss what is the best approach for save return value
    public WinningNumbers save(WinningNumbers winningNumbers) {
        if (!containsNumbersOfDrawDate(winningNumbers.drawDate())) {
            databaseInMemory.put(winningNumbers.drawDate(), winningNumbers);
        }
        return winningNumbers;
    }

    @Override
    public Optional<WinningNumbers> getWinningNumbersForDrawDate(LocalDateTime drawDate) {
        return Optional.ofNullable(databaseInMemory.get(drawDate));
    }

    @Override
    public boolean containsNumbersOfDrawDate(LocalDateTime drawDate) {
        return databaseInMemory.containsKey(drawDate);
    }
}
