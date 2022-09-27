package pl.lotto.resultschecker;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.lotto.numberreceiver.NumberReceiverFacade;
import pl.lotto.winningnumbergenerator.WinningNumberGeneratorFacade;

@Configuration
public class ResultsCheckerConfiguration {

    public ResultsCheckerFacade createForTests(NumberReceiverFacade numberReceiverFacade, WinningNumberGeneratorFacade winningNumbersFacade,
                                               ResultsCheckerRepository resultsCheckerRepository, CheckerConfigurable winningConfig) {

        ResultsChecker resultsChecker = new ResultsChecker(winningConfig);
        LotteryResultsGenerator lotteryResultsGenerator = new LotteryResultsGenerator(resultsChecker);
        return new ResultsCheckerFacade(numberReceiverFacade, winningNumbersFacade, lotteryResultsGenerator, resultsCheckerRepository);
    }

    @Bean("resultsCheckerFacade")
    public ResultsCheckerFacade createForProduction(NumberReceiverFacade numberReceiverFacade, WinningNumberGeneratorFacade winningNumbersFacade,
                                                    ResultsCheckerRepository resultsCheckerRepository, CheckerConfigurable winningConfig) {

        ResultsChecker resultsChecker = new ResultsChecker(winningConfig);
        LotteryResultsGenerator lotteryResultsGenerator = new LotteryResultsGenerator(resultsChecker);
        return new ResultsCheckerFacade(numberReceiverFacade, winningNumbersFacade, lotteryResultsGenerator, resultsCheckerRepository);
    }

}
