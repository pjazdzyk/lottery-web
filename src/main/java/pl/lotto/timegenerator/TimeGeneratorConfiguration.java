package pl.lotto.timegenerator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;

@Configuration
public class TimeGeneratorConfiguration {

    public TimeGeneratorFacade createForTest(Clock clockForTest, DayOfWeek drawDayOfWeek, LocalTime drawTime, Duration expirationInDays) {
        ExpirationDateTimeGenerator expirationDateTimeGenerator = new ExpirationDateTimeGenerator(expirationInDays);
        CurrentDateTimeGenerator currentDateTimeGenerator = new CurrentDateTimeGenerator(clockForTest);
        DrawDateTimeGenerator drawDateTimeGenerator = new DrawDateTimeGenerator(drawDayOfWeek, drawTime);
        TimeGenerator timeGenerator = new TimeGenerator(currentDateTimeGenerator, drawDateTimeGenerator, expirationDateTimeGenerator);
        return new TimeGeneratorFacade(timeGenerator);
    }

    @Bean
    @Profile("integration")
    public TimeGeneratorFacade createForIntegration(Clock clockForTest) {
        DayOfWeek drawDayOfWeek = DayOfWeek.SATURDAY;
        LocalTime drawTime = LocalTime.of(12, 0);
        Duration expirationInDays = Duration.ofDays(365 * 2);
        return createForTest(clockForTest, drawDayOfWeek, drawTime, expirationInDays);
    }

    @Bean
    @Profile("production")
    public TimeGeneratorFacade createForProduction() {
        DayOfWeek drawDayOfWeek = DayOfWeek.SATURDAY;
        LocalTime drawTime = LocalTime.of(12, 0);
        Clock clock = Clock.systemUTC();
        Duration expirationInDays = Duration.ofDays(365 * 2);
        ExpirationDateTimeGenerator expirationDateTimeGenerator = new ExpirationDateTimeGenerator(expirationInDays);
        CurrentDateTimeGenerator currentDateTimeGenerator = new CurrentDateTimeGenerator(clock);
        DrawDateTimeGenerator drawDateTimeGenerator = new DrawDateTimeGenerator(drawDayOfWeek, drawTime);
        TimeGenerator timeGenerator = new TimeGenerator(currentDateTimeGenerator, drawDateTimeGenerator, expirationDateTimeGenerator);
        return new TimeGeneratorFacade(timeGenerator);
    }

}
