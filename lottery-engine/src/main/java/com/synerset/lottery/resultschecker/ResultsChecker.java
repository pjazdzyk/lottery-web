package com.synerset.lottery.resultschecker;

import java.util.List;

class ResultsChecker {

    private final CheckerConfigurable winningPropertyConfig;

    ResultsChecker(CheckerConfigurable winningPropertyConfig) {
        this.winningPropertyConfig = winningPropertyConfig;
    }

    List<Integer> findMatchedNumbers(List<Integer> userTypedNumbers, List<Integer> winningNumbers) {
        return userTypedNumbers.stream().filter(winningNumbers::contains).toList();
    }

    boolean checkIfIsWinner(List<Integer> matchedNumbers) {
        return matchedNumbers.size() >= winningPropertyConfig.getMatchedNumbersToWin();
    }

}
