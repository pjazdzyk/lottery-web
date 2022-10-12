package pl.lotto.timegenerator;

import java.time.*;

public interface TimeConfigurable {
    DayOfWeek getDrawDayOfWeek();

    void setDrawDayOfWeek(DayOfWeek drawDayOfWeek);

    LocalTime getDrawTime();

    void setDrawTime(LocalTime drawTime);

    Duration getExpirationInDays();

    void setExpirationInDays(Duration expirationInDays);

}
