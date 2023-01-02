package com.synerset.lottery.timegenerator;

import com.synerset.lottery.IntegrationTestConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.Clock;

@Configuration
@Profile("integration")
class TimeGeneratorConfigurationIntegration implements IntegrationTestConstants {

    @Bean
    public TimeGeneratorFacade createForTest(TimeConfigurable timeSpec) {
        Clock integrationClock = ProgressingAdjustableClock.ofLocalDateAndLocalTime(INITIAL_DATE, INITIAL_TIME, ZONE_ID);
        ExpirationDateTimeGenerator expirationDateTimeGenerator = new ExpirationDateTimeGenerator(timeSpec.getExpirationInDays());
        CurrentDateTimeGenerator currentDateTimeGenerator = new CurrentDateTimeGenerator(integrationClock);
        DrawDateTimeGenerator drawDateTimeGenerator = new DrawDateTimeGenerator(timeSpec.getDrawDayOfWeek(), timeSpec.getDrawTime());
        TimeGenerator timeGenerator = new TimeGenerator(currentDateTimeGenerator, drawDateTimeGenerator, expirationDateTimeGenerator);
        return new TimeGeneratorFacadeImpl(timeGenerator);
    }

}
