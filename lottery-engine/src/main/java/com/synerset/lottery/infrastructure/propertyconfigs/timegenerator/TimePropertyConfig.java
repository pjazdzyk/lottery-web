package com.synerset.lottery.infrastructure.propertyconfigs.timegenerator;

import com.synerset.lottery.timegenerator.TimeConfigurable;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;

@Configuration
@ConfigurationProperties(prefix = "lotto.time")
class TimePropertyConfig implements TimeConfigurable {

    private DayOfWeek drawDayOfWeek;
    private LocalTime drawHour;
    private Duration expirationInDays;

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

    @Override
    public String toString() {
        return "TimePropertyConfig{" +
                "drawDayOfWeek=" + drawDayOfWeek +
                ", drawHour=" + drawHour +
                ", expirationInDays=" + expirationInDays.toDays() +
                '}';
    }
}


