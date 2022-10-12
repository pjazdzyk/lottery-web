package pl.lotto.infrastructure.resultschecker.propertyreadconfig;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import pl.lotto.resultschecker.CheckerConfigurable;

@Configuration
@ConfigurationProperties(prefix = "lotto.checker")
class CheckerPropertyConfig implements CheckerConfigurable {

    private int matchedNumbersToWin;

    public int getMatchedNumbersToWin() {
        return matchedNumbersToWin;
    }

    public void setMatchedNumbersToWin(int matchedNumbersToWin) {
        this.matchedNumbersToWin = matchedNumbersToWin;
    }

    @Override
    public String toString() {
        return "WinningPropertyConfig{" +
                "matchedNumbersToWin=" + matchedNumbersToWin +
                '}';
    }
}
