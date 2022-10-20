package pl.lottery.infrastructure.resultschecker.schedulers;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.lottery.resultschecker.WinningNumberGeneratorPort;
import pl.lottery.resultschecker.ResultsCheckerFacade;
import pl.lottery.timegenerator.TimeGeneratorFacade;

import java.time.LocalDateTime;

@Service
class LotteryScheduler {

    private final WinningNumberGeneratorPort winningNumberGeneratorPort;
    private final ResultsCheckerFacade resultsCheckerFacade;
    private final TimeGeneratorFacade timeGeneratorFacade;
    private LocalDateTime drawDate;

    public LotteryScheduler(WinningNumberGeneratorPort winningNumberGeneratorPort, ResultsCheckerFacade resultsCheckerFacade,
                            TimeGeneratorFacade timeGeneratorFacade) {

        this.winningNumberGeneratorPort = winningNumberGeneratorPort;
        this.resultsCheckerFacade = resultsCheckerFacade;
        this.timeGeneratorFacade = timeGeneratorFacade;
        this.drawDate = timeGeneratorFacade.getDrawDateAndTime();
    }

    @Scheduled(cron = "${lotto.checker.lotteryRunOccurrence}")
    void runLottery(){
        winningNumberGeneratorPort.generateWinningNumbers(drawDate);
        resultsCheckerFacade.generateLotteryResultsForDrawDate(drawDate);
        drawDate = timeGeneratorFacade.getDrawDateAndTime();
    }

}
