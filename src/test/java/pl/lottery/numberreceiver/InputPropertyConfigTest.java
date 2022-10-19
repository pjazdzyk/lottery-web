package pl.lottery.numberreceiver;

public class InputPropertyConfigTest implements InputConfigurable {

    private final int minNumber;
    private final int maxNumber;
    private final int numberCount;

    public InputPropertyConfigTest(int minNumber, int maxNumber, int numberCount) {
        this.minNumber = minNumber;
        this.maxNumber = maxNumber;
        this.numberCount = numberCount;
    }

    @Override
    public int getMinNumber() {
        return minNumber;
    }

    @Override
    public void setMinNumber(int minNumber) {

    }

    @Override
    public int getMaxNumber() {
        return maxNumber;
    }

    @Override
    public void setMaxNumber(int maxNumber) {

    }

    @Override
    public int getNumberCount() {
        return numberCount;
    }

    @Override
    public void setNumberCount(int numberCount) {

    }
}
