package pl.lotto.winningnumbergenerator;

public interface WinningPropertyConfigurable {

    int getMinWinNumber();

    void setMinWinNumber(int minWinNumber);

    int getMaxWinNumber();

    void setMaxWinNumber(int maxWinNumber);

    int getWinNumberCount();

    void setWinNumberCount(int winNumberCount);

}
