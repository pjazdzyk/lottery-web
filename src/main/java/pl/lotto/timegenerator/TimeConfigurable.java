package pl.lotto.timegenerator;

import java.time.*;

interface TimeConfigurable {
    DayOfWeek getDrawDayOfWeek();

    void setDrawDayOfWeek(DayOfWeek drawDayOfWeek);

    LocalTime getDrawTime();

    void setDrawTime(LocalTime drawTime);

    Duration getExpirationInDays();

    void setExpirationInDays(Duration expirationInDays);

}
