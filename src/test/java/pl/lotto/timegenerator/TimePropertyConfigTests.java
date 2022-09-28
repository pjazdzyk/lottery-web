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
    public LocalTime getDrawHour() {
        return drawHour;
    }

    @Override
    public void setDrawHour(LocalTime drawHour) {
        this.drawHour = drawHour;
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