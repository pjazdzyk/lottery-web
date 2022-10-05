package pl.lotto.winningnumbergenerator;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.lotto.timegenerator.TimeGeneratorFacade;

import java.time.LocalDateTime;

@Service
public class WinningSchedulerConfiguration {

    private final WinningNumberGeneratorFacade winningNumberGeneratorFacade;
    private final TimeGeneratorFacade timeGeneratorFacade;
    private LocalDateTime drawDate;

    WinningSchedulerConfiguration(WinningNumberGeneratorFacade winningNumberGeneratorFacade, TimeGeneratorFacade timeGeneratorFacade) {
        this.winningNumberGeneratorFacade = winningNumberGeneratorFacade;
        this.timeGeneratorFacade = timeGeneratorFacade;
        this.drawDate = timeGeneratorFacade.getDrawDateAndTime();
    }

    @Scheduled(cron = "${lotto.winning.lotteryRunOccurrence}")
    void generateNextWinningNumbers(){
        winningNumberGeneratorFacade.generateWinningNumbers(drawDate);
        drawDate = timeGeneratorFacade.getDrawDateAndTime();
    }

}
