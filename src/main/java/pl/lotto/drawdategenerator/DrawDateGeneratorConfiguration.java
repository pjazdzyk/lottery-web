package pl.lotto.drawdategenerator;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.LocalTime;

public class DrawDateGeneratorConfiguration {

    DrawDateGeneratorFacade createForTest(Clock clockForTest) {
        DayOfWeek defDrawDayOfWeek = DayOfWeek.SATURDAY;
        LocalTime defDrawTime = LocalTime.of(20, 0);
        DrawDateGenerator drawDateGenerator = new DrawDateGenerator(defDrawDayOfWeek, defDrawTime, clockForTest);
        CurrentTimeGenerator currentTimeGenerator = new CurrentTimeGenerator(clockForTest);
        return new DrawDateGeneratorFacade(drawDateGenerator, currentTimeGenerator);
    }

    DrawDateGeneratorFacade createForProduction() {
        DayOfWeek defDrawDayOfWeek = DayOfWeek.SATURDAY;
        LocalTime defDrawTime = LocalTime.of(20, 0);
        Clock clock = Clock.systemUTC();
        DrawDateGenerator drawDateGenerator = new DrawDateGenerator(defDrawDayOfWeek, defDrawTime, clock);
        CurrentTimeGenerator currentTimeGenerator = new CurrentTimeGenerator(clock);
        return new DrawDateGeneratorFacade(drawDateGenerator, currentTimeGenerator);
    }
}
