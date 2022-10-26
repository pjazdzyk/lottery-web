package pl.lottery.infrastructure.schedulers.resultschecker;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.lottery.resultschecker.ResultsCheckerFacade;
import pl.lottery.timegenerator.TimeGeneratorFacade;

import java.time.LocalDateTime;

@Service
class LotteryScheduler {

    private final ResultsCheckerFacade resultsCheckerFacade;
    private final TimeGeneratorFacade timeGeneratorFacade;
    private LocalDateTime drawDate;

    public LotteryScheduler(ResultsCheckerFacade resultsCheckerFacade, TimeGeneratorFacade timeGeneratorFacade) {
        this.resultsCheckerFacade = resultsCheckerFacade;
        this.timeGeneratorFacade = timeGeneratorFacade;
        this.drawDate = timeGeneratorFacade.getNextDrawDateAndTime();
    }

    @Scheduled(cron = "${lotto.checker.lotteryRunOccurrence}")
    void runLottery() {
        resultsCheckerFacade.generateLotteryResultsForDrawDate(drawDate);
        this.drawDate = timeGeneratorFacade.getNextDrawDateAndTime();
    }

}
