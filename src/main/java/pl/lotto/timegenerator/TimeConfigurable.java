package pl.lotto.timegenerator;

import java.time.*;

interface TimeConfigurable {
    DayOfWeek getDrawDayOfWeek();

    void setDrawDayOfWeek(DayOfWeek drawDayOfWeek);

    LocalTime getDrawHour();

    void setDrawHour(LocalTime drawHour);

    Duration getExpirationInDays();

    void setExpirationInDays(Duration expirationInDays);

    LocalDate getSampleDate();

    void setSampleDate(LocalDate sampleDate);

    LocalTime getSampleTime();

    void setSampleTime(LocalTime sampleTime);

    ZoneId getZoneId();

    void setZoneId(ZoneId zoneId);
}
