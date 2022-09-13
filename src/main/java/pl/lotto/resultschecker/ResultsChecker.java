package pl.lotto.resultschecker;

import java.util.List;

class ResultsChecker {

    private final int MATCHES_TO_WIN_CRITERIA;

    public ResultsChecker(int minNumberOfMatchesToWin) {
        this.MATCHES_TO_WIN_CRITERIA = minNumberOfMatchesToWin;
    }

    List<Integer> getMatchedNumbers(List<Integer> userTypedNumbers, List<Integer> winningNumbers) {
        return userTypedNumbers.stream().filter(winningNumbers::contains).toList();
    }

    boolean checkIfIsWinner(List<Integer> matchedNumbers) {
        return matchedNumbers.size() >= MATCHES_TO_WIN_CRITERIA;
    }

}
