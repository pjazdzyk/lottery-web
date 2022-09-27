package pl.lotto.timegenerator;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.time.*;

@Configuration
@PropertySource(value = "classpath:/application-${spring.profiles.active}.yml")
@ConfigurationProperties(prefix = "lotto.time")
class TimePropertyConfig implements TimeConfigurable {

    private DayOfWeek drawDayOfWeek;
    private LocalTime drawHour;
    private Duration expirationInDays;
    private LocalDate sampleDate;
    private LocalTime sampleTime;
    private ZoneId zoneId;

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

    @Override
    public String toString() {
        return "TimePropertyConfig{" +
                "drawDayOfWeek=" + drawDayOfWeek +
                ", drawHour=" + drawHour +
                ", expirationInDays=" + expirationInDays.toDays() +
                ", sampleDate=" + sampleDate +
                ", sampleTime=" + sampleTime +
                ", zoneId=" + zoneId +
                '}';
    }
}


