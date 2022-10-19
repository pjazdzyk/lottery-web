package pl.lotto.resultschecker;

public interface CheckerConfigurable {
    int getMatchedNumbersToWin();

    void setMatchedNumbersToWin(int matchedNumbersToWin);

    String getLotteryRunOccurrence();

    void setLotteryRunOccurrence(String lotteryRunOccurrence);
}
