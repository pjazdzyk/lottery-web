package pl.lotto.resultschecker;

public class CheckerPropertyConfigTest implements CheckerConfigurable {

    private int matchedNumbersToWin;

    public CheckerPropertyConfigTest(int matchedNumbersToWin) {
        this.matchedNumbersToWin = matchedNumbersToWin;
    }

    public int getMatchedNumbersToWin() {
        return matchedNumbersToWin;
    }

    public void setMatchedNumbersToWin(int matchedNumbersToWin) {
        this.matchedNumbersToWin = matchedNumbersToWin;
    }

}
