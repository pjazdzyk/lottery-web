package pl.lottery.timegenerator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import pl.lottery.IntegrationTestConstants;

import java.time.Clock;

@Configuration
@Profile("integration")
public class TimeGeneratorConfigurationIntegration implements IntegrationTestConstants {

    @Bean("adjustableClock")
    public Clock createProgressingAdjustableClock() {
        return ProgressingAdjustableClock.ofLocalDateAndLocalTime(INITIAL_DATE, INITIAL_TIME, ZONE_ID);
    }

}
