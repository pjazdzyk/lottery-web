package com.synerset.lottery.timegenerator;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;

public interface TimeConfigurable {
    DayOfWeek getDrawDayOfWeek();

    void setDrawDayOfWeek(DayOfWeek drawDayOfWeek);

    LocalTime getDrawTime();

    void setDrawTime(LocalTime drawTime);

    Duration getExpirationInDays();

    void setExpirationInDays(Duration expirationInDays);

}
