package pl.lotto.timegenerator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;

@Profile("integration")
@Configuration
public class TimeGeneratorConfigurationIntegration {

    private final LocalDate initialDate = LocalDate.of(2022, 8, 8);
    private final LocalTime initialTime = LocalTime.of(15, 15);
    private final ZoneId zoneId = ZoneId.systemDefault();

    @Bean("adjustableClock")
    public Clock createProgressingAdjustableClock() {
        return ProgressingAdjustableClock.ofLocalDateAndLocalTime(initialDate, initialTime, zoneId);
    }

}
