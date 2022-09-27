package pl.lotto.timegenerator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class TimeGeneratorConfigurationIntegration {

    @Profile("integration")
    @Bean("integrationAdjustableClock")
    AdjustableClock createAdjustableClock(TimeConfigurable timeConfig){
        return AdjustableClock.ofTimeConfiguration(timeConfig);
    }

    @Profile("integration")
    @Bean("integrationTimeGeneratorFacade")
    public TimeGeneratorFacade createForIntegration(TimePropertyConfig timeConfig, AdjustableClock clockForTest) {
        ExpirationDateTimeGenerator expirationDateTimeGenerator = new ExpirationDateTimeGenerator(timeConfig.getExpirationInDays());
        CurrentDateTimeGenerator currentDateTimeGenerator = new CurrentDateTimeGenerator(clockForTest);
        DrawDateTimeGenerator drawDateTimeGenerator = new DrawDateTimeGenerator(timeConfig.getDrawDayOfWeek(), timeConfig.getDrawHour());
        TimeGenerator timeGenerator = new TimeGenerator(currentDateTimeGenerator, drawDateTimeGenerator, expirationDateTimeGenerator);
        return new TimeGeneratorFacade(timeGenerator);
    }

}
