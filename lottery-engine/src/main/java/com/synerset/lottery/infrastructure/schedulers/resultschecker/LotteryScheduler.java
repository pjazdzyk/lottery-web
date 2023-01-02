package com.synerset.lottery.infrastructure.schedulers.resultschecker;

import com.synerset.lottery.resultschecker.ResultsCheckerFacade;
import com.synerset.lottery.timegenerator.TimeGeneratorFacade;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
class LotteryScheduler {

    private final ResultsCheckerFacade resultsCheckerFacade;
    private final TimeGeneratorFacade timeGeneratorFacade;
    private LocalDateTime drawDate;

    public LotteryScheduler(ResultsCheckerFacade resultsCheckerFacade, TimeGeneratorFacade timeGeneratorFacade) {
        this.resultsCheckerFacade = resultsCheckerFacade;
        this.timeGeneratorFacade = timeGeneratorFacade;
        this.drawDate = timeGeneratorFacade.retrieveNextDrawDateAndTime();
    }

    @Scheduled(cron = "${lotto.checker.lotteryRunOccurrence}")
    void runLottery() {
        resultsCheckerFacade.generateLotteryResultsForDrawDate(drawDate);
        this.drawDate = timeGeneratorFacade.retrieveNextDrawDateAndTime();
    }

}
