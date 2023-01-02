package com.synerset.lottery.resultschecker;

public interface CheckerConfigurable {
    int getMatchedNumbersToWin();

    void setMatchedNumbersToWin(int matchedNumbersToWin);

    String getLotteryRunOccurrence();

    void setLotteryRunOccurrence(String lotteryRunOccurrence);
}
