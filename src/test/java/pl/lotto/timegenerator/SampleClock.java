package pl.lotto.timegenerator;

import java.time.*;

public interface SampleClock {

    default Clock sampleClock(LocalDate date, LocalTime time) {
        ZonedDateTime zoneDateTime = ZonedDateTime.of(date.getYear(), date.getMonthValue(), date.getDayOfMonth(),
                time.getHour(), time.getMinute(), time.getSecond(), time.getNano(), ZoneId.systemDefault());
        return Clock.fixed(zoneDateTime.toInstant(), ZoneId.systemDefault());
    }

}
