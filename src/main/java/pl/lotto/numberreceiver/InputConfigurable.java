package pl.lotto.numberreceiver;

public interface InputConfigurable {
    int getMinNumber();

    void setMinNumber(int minNumber);

    int getMaxNumber();

    void setMaxNumber(int maxNumber);

    int getNumberCount();

    void setNumberCount(int numberCount);
}
