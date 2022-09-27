package pl.lotto.timegenerator;

import java.time.*;

public class TimePropertyConfigTests implements TimeConfigurable {

    private DayOfWeek drawDayOfWeek;
    private LocalTime drawHour;
    private Duration expirationInDays;
    private LocalDate sampleDate;
    private LocalTime sampleTime;
    private ZoneId zoneId;

    public TimePropertyConfigTests(DayOfWeek drawDayOfWeek, LocalTime drawHour, Duration expirationInDays,
                                   LocalDate sampleDate, LocalTime sampleTime, ZoneId zoneId) {

        this.drawDayOfWeek = drawDayOfWeek;
        this.drawHour = drawHour;
        this.expirationInDays = expirationInDays;
        this.sampleDate = sampleDate;
        this.sampleTime = sampleTime;
        this.zoneId = zoneId;
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

    @Override
    public LocalDate getSampleDate() {
        return sampleDate;
    }

    @Override
    public void setSampleDate(LocalDate sampleDate) {
        this.sampleDate = sampleDate;
    }

    @Override
    public LocalTime getSampleTime() {
        return sampleTime;
    }

    @Override
    public void setSampleTime(LocalTime sampleTime) {
        this.sampleTime = sampleTime;
    }

    @Override
    public ZoneId getZoneId() {
        return zoneId;
    }

    @Override
    public void setZoneId(ZoneId zoneId) {
        this.zoneId = zoneId;
    }
}