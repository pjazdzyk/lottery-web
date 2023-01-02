package com.synerset.lottery.lotterygenerator.winningnumbergenerator;

public class WinningPropertyPropertyConfigTest implements WinningPropertyConfigurable {

    private int minWinNumber;
    private int maxWinNumber;
    private int winNumberCount;

    public WinningPropertyPropertyConfigTest(int minWinNumber, int maxWinNumber, int winNumberCount) {
        this.minWinNumber = minWinNumber;
        this.maxWinNumber = maxWinNumber;
        this.winNumberCount = winNumberCount;
    }

    public int getMinWinNumber() {
        return minWinNumber;
    }

    public void setMinWinNumber(int minWinNumber) {
        this.minWinNumber = minWinNumber;
    }

    public int getMaxWinNumber() {
        return maxWinNumber;
    }

    public void setMaxWinNumber(int maxWinNumber) {
        this.maxWinNumber = maxWinNumber;
    }

    public int getWinNumberCount() {
        return winNumberCount;
    }

    public void setWinNumberCount(int winNumberCount) {
        this.winNumberCount = winNumberCount;
    }

}
