package pl.lotto.resultschecker;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

class ResultsCheckerRepositoryStub implements ResultCheckerRepository {

    private final Map<UUID, LotteryResults> databaseInMemory = new ConcurrentHashMap<>();

    @Override
    public LotteryResults save(LotteryResults lotteryResult) {
        databaseInMemory.put(lotteryResult.uuid(), lotteryResult);
        return lotteryResult;
    }

    @Override
    public int saveFromList(List<LotteryResults> lotteryResults) {
        lotteryResults.forEach(this::save);
        return lotteryResults.size();
    }

    @Override
    public Optional<LotteryResults> getLotteryResultsForUuid(UUID uuid) {
        return Optional.ofNullable(databaseInMemory.get(uuid));
    }

    @Override
    public Optional<LotteryResults> deleteLotteryResultsForUuid(UUID uuid) {
        return Optional.ofNullable(databaseInMemory.remove(uuid));
    }

    @Override
    public List<LotteryResults> getLotteryResultsForDrawDate(LocalDateTime drawDate) {
        return databaseInMemory.values()
                .stream()
                .filter(lotteryResult -> drawDate.isEqual(lotteryResult.drawDate()))
                .collect(Collectors.toList());
    }

    @Override
    public List<LotteryResults> deleteLotteryResultsForDrawDate(LocalDateTime drawDate) {
        List<LotteryResults> lotteryResults = new ArrayList<>();
        for(LotteryResults result : databaseInMemory.values()){
            if(result.drawDate().isEqual(drawDate)){
                lotteryResults.add(result);
                databaseInMemory.remove(result.uuid());
            }
        }
        return lotteryResults;
    }

    @Override
    public List<LotteryResults> getAllLotteryResults() {
        return new ArrayList<>(databaseInMemory.values());
    }
}
