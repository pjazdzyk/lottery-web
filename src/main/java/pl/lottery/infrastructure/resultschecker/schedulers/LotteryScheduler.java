package pl.lottery.infrastructure.resultschecker.schedulers;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.lottery.resultschecker.ResultsCheckerFacade;
import pl.lottery.timegenerator.TimeGeneratorFacade;
import pl.lottery.infrastructure.winningnumberservice.WinningNumberGenerable;

import java.time.LocalDateTime;

@Service
class LotteryScheduler {

    private final WinningNumberGenerable winningNumberGenerable;
    private final ResultsCheckerFacade resultsCheckerFacade;
    private final TimeGeneratorFacade timeGeneratorFacade;
    private LocalDateTime drawDate;

    public LotteryScheduler(WinningNumberGenerable winningNumberGenerable, ResultsCheckerFacade resultsCheckerFacade,
                            TimeGeneratorFacade timeGeneratorFacade) {

        this.winningNumberGenerable = winningNumberGenerable;
        this.resultsCheckerFacade = resultsCheckerFacade;
        this.timeGeneratorFacade = timeGeneratorFacade;
        this.drawDate = timeGeneratorFacade.getDrawDateAndTime();
    }

    @Scheduled(cron = "${lotto.checker.lotteryRunOccurrence}")
    void runLottery(){
        winningNumberGenerable.generateWinningNumbers(drawDate);
        resultsCheckerFacade.generateLotteryResultsForDrawDate(drawDate);
        drawDate = timeGeneratorFacade.getDrawDateAndTime();
    }

}
