package pl.lotto.timegenerator;

import java.time.*;

public class TimePropertyConfigTests implements TimeConfigurable {

    private DayOfWeek drawDayOfWeek;
    private LocalTime drawHour;
    private Duration expirationInDays;

    public TimePropertyConfigTests(DayOfWeek drawDayOfWeek, LocalTime drawHour, Duration expirationInDays) {

        this.drawDayOfWeek = drawDayOfWeek;
        this.drawHour = drawHour;
        this.expirationInDays = expirationInDays;
    }

    @Override
    public DayOfWeek getDrawDayOfWeek() {
        return drawDayOfWeek;
    }

    @Override
    public void setDrawDayOfWeek(DayOfWeek drawDayOfWeek) {
        this.drawDayOfWeek = drawDayOfWeek;
    }

    @Override
    public LocalTime getDrawTime() {
        return drawHour;
    }

    @Override
    public void setDrawTime(LocalTime drawTime) {
        this.drawHour = drawTime;
    }

    @Override
    public Duration getExpirationInDays() {
        return expirationInDays;
    }

    @Override
    public void setExpirationInDays(Duration expirationInDays) {
        this.expirationInDays = expirationInDays;
    }

}