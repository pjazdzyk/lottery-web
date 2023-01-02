package com.synerset.lottery.timegenerator;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

class DrawDateTimeGenerator {

    private final DayOfWeek drawDayOfWeek;
    private final LocalTime drawTime;

    DrawDateTimeGenerator(DayOfWeek drawDayOfWeek, LocalTime drawTime) {
        this.drawDayOfWeek = drawDayOfWeek;
        this.drawTime = drawTime;
    }

    LocalDateTime generateDrawDate(LocalDateTime currentDateTime) {
        LocalTime currentTime = currentDateTime.toLocalTime();
        LocalDate currentDate = currentDateTime.toLocalDate();
        DayOfWeek currentDayOfWeek = currentDateTime.getDayOfWeek();
        Duration daysToNextDraw = calculateDaysToNextDraw(currentTime, currentDayOfWeek);
        LocalDate expectedDrawDate = currentDate.plusDays(daysToNextDraw.toDays());
        return LocalDateTime.of(expectedDrawDate, drawTime);
    }

    private Duration calculateDaysToNextDraw(LocalTime currentTime, DayOfWeek currentDayOfWeek) {
        Duration daysToNextDraw;
        if (currentDayOfWeek == drawDayOfWeek && currentTime.isAfter(drawTime)) {
            daysToNextDraw = Duration.ofDays(DayOfWeek.values().length);
        } else {
            daysToNextDraw = determineDurationBetweenDaysOfWeek(currentDayOfWeek, drawDayOfWeek);
        }
        return daysToNextDraw;
    }

    private Duration determineDurationBetweenDaysOfWeek(DayOfWeek fromDay, DayOfWeek toDay) {
        int dayDifference = toDay.getValue() - fromDay.getValue();
        if (dayDifference < 0) {
            dayDifference = DayOfWeek.values().length + dayDifference;
        }
        return Duration.ofDays(dayDifference);
    }

}
