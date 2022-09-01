package pl.lotto.temporalgenerator;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;

public class TemporalGeneratorConfiguration {

    public TemporalGeneratorFacade createForTest(DayOfWeek drawDayOfWeek, LocalTime drawTime, Duration expirationInDays, Clock clockForTest) {
        CurrentDateTimeGenerator currentDateTimeGenerator = new CurrentDateTimeGenerator(clockForTest);
        DrawDateTimeGenerator drawDateTimeGenerator = new DrawDateTimeGenerator(drawDayOfWeek, drawTime);
        ExpirationDateTimeGenerator expirationDateTimeGenerator = new ExpirationDateTimeGenerator(expirationInDays);
        TemporalGenerator temporalGenerator = new TemporalGenerator(currentDateTimeGenerator, drawDateTimeGenerator, expirationDateTimeGenerator);
        return new TemporalGeneratorFacade(temporalGenerator);
    }

    public TemporalGeneratorFacade createForProduction() {
        DayOfWeek drawDayOfWeek = DayOfWeek.SATURDAY;
        LocalTime drawTime = LocalTime.of(20, 30);
        Clock clock = Clock.systemUTC();
        Duration expirationInDays = Duration.ofDays(365 * 2);
        ExpirationDateTimeGenerator expirationDateTimeGenerator = new ExpirationDateTimeGenerator(expirationInDays);
        CurrentDateTimeGenerator currentDateTimeGenerator = new CurrentDateTimeGenerator(clock);
        DrawDateTimeGenerator drawDateTimeGenerator = new DrawDateTimeGenerator(drawDayOfWeek, drawTime);
        TemporalGenerator temporalGenerator = new TemporalGenerator(currentDateTimeGenerator, drawDateTimeGenerator, expirationDateTimeGenerator);
        return new TemporalGeneratorFacade(temporalGenerator);
    }


}
