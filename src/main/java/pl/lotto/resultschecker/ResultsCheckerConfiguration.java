package pl.lotto.resultschecker;

import pl.lotto.numberreceiver.NumberReceiverFacade;
import pl.lotto.winningnumbergenerator.WinningNumberGeneratorFacade;

public class ResultsCheckerConfiguration {
    public ResultsCheckerFacade createForTests(NumberReceiverFacade numberReceiverFacade, WinningNumberGeneratorFacade winningNumbersFacade,
                                               ResultCheckerRepository resultCheckerRepository) {

        int minMatchesToWin = 3;
        WinningRules winningRules = new WinningRules(minMatchesToWin);
        ResultsChecker resultsChecker = new ResultsChecker(winningRules);
        LotteryResultsGenerator lotteryResultsGenerator = new LotteryResultsGenerator(resultsChecker);
        return new ResultsCheckerFacade(numberReceiverFacade, winningNumbersFacade, lotteryResultsGenerator, resultCheckerRepository);
    }
}
