package com.synerset.lottery.infrastructure.propertyconfigs.resultschecker;

import com.synerset.lottery.resultschecker.CheckerConfigurable;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "lotto.checker")
class CheckerPropertyConfig implements CheckerConfigurable {

    private int matchedNumbersToWin;

    private String lotteryRunOccurrence;

    @Override
    public int getMatchedNumbersToWin() {
        return matchedNumbersToWin;
    }

    @Override
    public void setMatchedNumbersToWin(int matchedNumbersToWin) {
        this.matchedNumbersToWin = matchedNumbersToWin;
    }

    @Override
    public String getLotteryRunOccurrence() {
        return lotteryRunOccurrence;
    }

    @Override
    public void setLotteryRunOccurrence(String lotteryRunOccurrence) {
        this.lotteryRunOccurrence = lotteryRunOccurrence;
    }

}
