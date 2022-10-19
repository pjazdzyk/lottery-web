package pl.lottery.resultschecker;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.lottery.numberreceiver.NumberReceiverFacade;
import pl.lottery.infrastructure.winningnumberservice.WinningNumberGenerable;

@Configuration
public class ResultsCheckerConfiguration {

    public ResultsCheckerFacade createForTests(NumberReceiverFacade numberReceiverFacade, WinningNumberGenerable winningNumbersFacade,
                                               ResultsCheckerRepository resultsCheckerRepository, CheckerConfigurable winningConfig) {

        ResultsChecker resultsChecker = new ResultsChecker(winningConfig);
        LotteryResultsGenerator lotteryResultsGenerator = new LotteryResultsGenerator(resultsChecker);
        return new ResultsCheckerFacade(numberReceiverFacade, winningNumbersFacade, lotteryResultsGenerator, resultsCheckerRepository);
    }

    @Bean("resultsCheckerFacade")
    public ResultsCheckerFacade createForProduction(NumberReceiverFacade numberReceiverFacade, WinningNumberGenerable winningNumbersService,
                                                    ResultsCheckerRepository resultsCheckerRepository, CheckerConfigurable winningConfig) {

        ResultsChecker resultsChecker = new ResultsChecker(winningConfig);
        LotteryResultsGenerator lotteryResultsGenerator = new LotteryResultsGenerator(resultsChecker);
        return new ResultsCheckerFacade(numberReceiverFacade, winningNumbersService, lotteryResultsGenerator, resultsCheckerRepository);
    }

}
