package pl.lotto.infrastructure.schedulers;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.lotto.resultschecker.ResultsCheckerFacade;
import pl.lotto.timegenerator.TimeGeneratorFacade;
import pl.lotto.winningnumbergenerator.WinningNumberGeneratorFacade;

import java.time.LocalDateTime;

@Service
class LotteryScheduler {

    private final WinningNumberGeneratorFacade winningNumberGeneratorFacade;
    private final ResultsCheckerFacade resultsCheckerFacade;
    private final TimeGeneratorFacade timeGeneratorFacade;
    private LocalDateTime drawDate;

    public LotteryScheduler(WinningNumberGeneratorFacade winningNumberGeneratorFacade, ResultsCheckerFacade resultsCheckerFacade,
                            TimeGeneratorFacade timeGeneratorFacade) {

        this.winningNumberGeneratorFacade = winningNumberGeneratorFacade;
        this.resultsCheckerFacade = resultsCheckerFacade;
        this.timeGeneratorFacade = timeGeneratorFacade;
        this.drawDate = timeGeneratorFacade.getDrawDateAndTime();
    }

    @Scheduled(cron = "${lotto.winning.lotteryRunOccurrence}")
    void runLottery(){
        System.out.println("Test");
        winningNumberGeneratorFacade.generateWinningNumbers(drawDate);
        resultsCheckerFacade.generateLotteryResultsForDrawDate(drawDate);
        drawDate = timeGeneratorFacade.getDrawDateAndTime();
    }

}
