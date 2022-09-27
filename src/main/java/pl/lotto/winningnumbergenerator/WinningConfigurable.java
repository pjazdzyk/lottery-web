package pl.lotto.winningnumbergenerator;

interface WinningConfigurable {

    int getMinWinNumber();

    void setMinWinNumber(int minWinNumber);

    int getMaxWinNumber();

    void setMaxWinNumber(int maxWinNumber);

    int getWinNumberCount();

    void setWinNumberCount(int winNumberCount);

}
