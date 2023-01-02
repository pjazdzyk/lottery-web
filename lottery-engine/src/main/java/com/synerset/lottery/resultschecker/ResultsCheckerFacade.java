package com.synerset.lottery.resultschecker;

import com.synerset.lottery.resultschecker.dto.CheckerDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ResultsCheckerFacade {
    int generateLotteryResultsForDrawDate(LocalDateTime drawDate);

    CheckerDto findResultsForId(UUID uuid);

    CheckerDto deleteLotteryResultsForUuid(UUID uuid);

    List<CheckerDto> findLotteryResultsForDrawDate(LocalDateTime drawDate);

    List<CheckerDto> findLotteryResultsDrawDateWinnersOnly(LocalDateTime drawDate);

    List<CheckerDto> findAllLotteryResults();

    boolean containsResultsForDrawDate(LocalDateTime drawDate);
}
