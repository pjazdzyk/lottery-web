package pl.lotto.winningnumbergenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

class WinningNumberRepositoryStub implements WinningNumberRepository {

    Map<LocalDateTime, WinningNumbers> databaseInMemory = new ConcurrentHashMap<>();

    @Override //TODO to discuss what is the best approach for save return value
    public WinningNumbers save(WinningNumbers winningNumbers) {
        if (!databaseInMemory.containsKey(winningNumbers.drawDate())) {
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

    @Override
    public Optional<WinningNumbers> deleteWinningNumbersForDate(LocalDateTime drawDate) {
        return Optional.ofNullable(databaseInMemory.remove(drawDate));
    }

    @Override
    public List<WinningNumbers> getAllWinningNumbers() {
        return new ArrayList<>(databaseInMemory.values());
    }

    @Override
    public boolean contains(WinningNumbers winningNumbers) {
        return databaseInMemory.containsKey(winningNumbers.drawDate());
    }
}
