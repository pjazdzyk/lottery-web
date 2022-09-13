package pl.lotto.resultschecker;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

interface ResultCheckerRepository {

    LotteryResults save(LotteryResults lotteryResult);

    int saveList(List<LotteryResults> lotteryResults);

    Optional<LotteryResults> getLotteryResultsForUuid(UUID uuid);

    Optional<LotteryResults> deleteLotteryResultsForUuid(UUID uuid);

    List<LotteryResults> getLotteryResultsForDrawDate(LocalDateTime drawDate);

    List<LotteryResults> deleteLotteryResultsForDrawDate(LocalDateTime drawDate);

    List<LotteryResults> getAllLotteryResults();

    boolean contains(LotteryResults lotteryResults);

    List<LotteryResults> getLotteryResultsDrawDateWinnersOnly(LocalDateTime drawDate);
}



