package pl.lotto;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.lotto.timegenerator.AdjustableClock;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;

@Configuration
class BeansForTestConfiguration {

    @Bean
    public Clock adjustableClock() {
        LocalDate sampleDate = LocalDate.of(2022, 8, 8);
        LocalTime sampleTime = LocalTime.of(8, 0);
        ZoneId sampleZone = ZoneId.systemDefault();
        return AdjustableClock.ofLocalDateAndLocalTime(sampleDate, sampleTime, sampleZone);
    }

}
