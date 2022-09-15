package pl.lotto.resultschecker;

import java.util.List;

class ResultsChecker {

    WinningRules winningRules;

    public ResultsChecker(WinningRules winningRules) {
        this.winningRules = winningRules;
    }

    List<Integer> getMatchedNumbers(List<Integer> userTypedNumbers, List<Integer> winningNumbers) {
        return userTypedNumbers.stream().filter(winningNumbers::contains).toList();
    }

    boolean checkIfIsWinner(List<Integer> matchedNumbers) {
        return matchedNumbers.size() >= winningRules.minimumMatchesCountToWin();
    }

}
