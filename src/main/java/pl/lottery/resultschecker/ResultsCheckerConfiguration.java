package pl.lottery.resultschecker;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.lottery.numberreceiver.NumberReceiverFacade;

@Configuration
public class ResultsCheckerConfiguration {

    public ResultsCheckerFacade createForTests(NumberReceiverFacade numberReceiverFacade, WinningNumberGeneratorPort winningNumbersFacade,
                                               ResultsCheckerRepository resultsCheckerRepository, CheckerConfigurable winningConfig) {

        ResultsChecker resultsChecker = new ResultsChecker(winningConfig);
        LotteryResultsGenerator lotteryResultsGenerator = new LotteryResultsGenerator(resultsChecker);
        return new ResultsCheckerFacade(numberReceiverFacade, winningNumbersFacade, lotteryResultsGenerator, resultsCheckerRepository);
    }

    @Bean("resultsCheckerFacade")
    public ResultsCheckerFacade createForProduction(NumberReceiverFacade numberReceiverFacade, WinningNumberGeneratorPort winningNumbersService,
                                                    ResultsCheckerRepository resultsCheckerRepository, CheckerConfigurable winningConfig) {

        ResultsChecker resultsChecker = new ResultsChecker(winningConfig);
        LotteryResultsGenerator lotteryResultsGenerator = new LotteryResultsGenerator(resultsChecker);
        return new ResultsCheckerFacade(numberReceiverFacade, winningNumbersService, lotteryResultsGenerator, resultsCheckerRepository);
    }

}
