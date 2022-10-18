package pl.lotto.infrastructure.resultschecker.schedulers;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.lotto.resultschecker.ResultsCheckerFacade;
import pl.lotto.timegenerator.TimeGeneratorFacade;
import pl.lotto.infrastructure.winningnumberservice.WinningNumberGenerable;

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

    @Scheduled(cron = "${lotto.winning.lotteryRunOccurrence}")
    void runLottery(){
        winningNumberGenerable.generateWinningNumbers(drawDate);
        resultsCheckerFacade.generateLotteryResultsForDrawDate(drawDate);
        drawDate = timeGeneratorFacade.getDrawDateAndTime();
    }

}
