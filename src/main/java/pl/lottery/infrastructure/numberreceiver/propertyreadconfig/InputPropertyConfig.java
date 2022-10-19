package pl.lottery.infrastructure.numberreceiver.propertyreadconfig;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import pl.lottery.numberreceiver.InputConfigurable;

@Configuration
@ConfigurationProperties(prefix = "lotto.input")
class InputPropertyConfig implements InputConfigurable {

    private int minNumber;
    private int maxNumber;
    private int numberCount;

    @Override
    public int getMinNumber() {
        return minNumber;
    }

    @Override
    public void setMinNumber(int minNumber) {
        this.minNumber = minNumber;
    }

    @Override
    public int getMaxNumber() {
        return maxNumber;
    }

    @Override
    public void setMaxNumber(int maxNumber) {
        this.maxNumber = maxNumber;
    }

    @Override
    public int getNumberCount() {
        return numberCount;
    }

    @Override
    public void setNumberCount(int numberCount) {
        this.numberCount = numberCount;
    }

    @Override
    public String toString() {
        return "InputPropertyConfig{" +
                "minNumber=" + minNumber +
                ", maxNumber=" + maxNumber +
                ", numberCount=" + numberCount +
                '}';
    }
}
