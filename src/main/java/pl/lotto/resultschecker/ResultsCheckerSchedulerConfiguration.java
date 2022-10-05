package pl.lotto.resultschecker;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.lotto.timegenerator.TimeGeneratorFacade;

import java.time.LocalDateTime;

@Service
public class ResultsCheckerSchedulerConfiguration {

    private final ResultsCheckerFacade resultsCheckerFacade;
    private final TimeGeneratorFacade timeGeneratorFacade;
    private LocalDateTime drawDate;

    public ResultsCheckerSchedulerConfiguration(ResultsCheckerFacade resultsCheckerFacade, TimeGeneratorFacade timeGeneratorFacade) {
        this.resultsCheckerFacade = resultsCheckerFacade;
        this.timeGeneratorFacade = timeGeneratorFacade;
        this.drawDate = timeGeneratorFacade.getDrawDateAndTime();
    }

    //TODO Somhow to implemnet a delay? Somehow enforce it to wait after winningNumbers are generated?
    @Scheduled(cron = "${lotto.winning.lotteryRunOccurrence}")
    void generateNextWinningNumbers(){
        resultsCheckerFacade.generateLotteryResultsForDrawDate(drawDate);
        drawDate = timeGeneratorFacade.getDrawDateAndTime();
    }

}
