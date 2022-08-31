package pl.lotto.drawdategenerator;

import java.time.Clock;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public interface SampleClock {

    default Clock sampleClock(int year, int month, int dayOfMonth) {
        ZonedDateTime of = ZonedDateTime.of(year, month, dayOfMonth, 14, 13, 0, 0, ZoneId.systemDefault());
        return Clock.fixed(of.toInstant(), ZoneId.systemDefault());
    }
}
