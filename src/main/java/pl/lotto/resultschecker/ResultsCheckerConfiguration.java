package pl.lotto.resultschecker;

import pl.lotto.numberreceiver.NumberReceiverFacade;
import pl.lotto.winningnumbergenerator.WinningNumberGeneratorFacade;

public class ResultsCheckerConfiguration {
    public ResultsCheckerFacade createForTests(NumberReceiverFacade numberReceiverFacade, WinningNumberGeneratorFacade winningNumbersFacade,
                                               ResultCheckerRepository resultCheckerRepository) {

        int MIN_MATCHES_WIN_CRITERIA = 3;
        ResultsChecker resultsChecker = new ResultsChecker(MIN_MATCHES_WIN_CRITERIA);
        LotteryResultsGenerator lotteryResultsGenerator = new LotteryResultsGenerator(resultsChecker);
        return new ResultsCheckerFacade(numberReceiverFacade, winningNumbersFacade, lotteryResultsGenerator, resultCheckerRepository);
    }
}
