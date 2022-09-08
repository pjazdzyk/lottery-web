package pl.lotto.timegenerator;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;

public class TimeGeneratorConfiguration {

    public TimeGeneratorFacade createForTest(Clock clockForTest, DayOfWeek drawDayOfWeek, LocalTime drawTime, Duration expirationInDays) {
        ExpirationDateTimeGenerator expirationDateTimeGenerator = new ExpirationDateTimeGenerator(expirationInDays);
        CurrentDateTimeGenerator currentDateTimeGenerator = new CurrentDateTimeGenerator(clockForTest);
        DrawDateTimeGenerator drawDateTimeGenerator = new DrawDateTimeGenerator(drawDayOfWeek, drawTime);
        TimeGenerator timeGenerator = new TimeGenerator(currentDateTimeGenerator, drawDateTimeGenerator, expirationDateTimeGenerator);
        return new TimeGeneratorFacade(timeGenerator);
    }

    public TimeGeneratorFacade createForProduction() {
        DayOfWeek drawDayOfWeek = DayOfWeek.SATURDAY;
        LocalTime drawTime = LocalTime.of(20, 30);
        Clock clock = Clock.systemUTC();
        Duration expirationInDays = Duration.ofDays(365 * 2);
        ExpirationDateTimeGenerator expirationDateTimeGenerator = new ExpirationDateTimeGenerator(expirationInDays);
        CurrentDateTimeGenerator currentDateTimeGenerator = new CurrentDateTimeGenerator(clock);
        DrawDateTimeGenerator drawDateTimeGenerator = new DrawDateTimeGenerator(drawDayOfWeek, drawTime);
        TimeGenerator timeGenerator = new TimeGenerator(currentDateTimeGenerator, drawDateTimeGenerator, expirationDateTimeGenerator);
        return new TimeGeneratorFacade(timeGenerator);
    }

}
