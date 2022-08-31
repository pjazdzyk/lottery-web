package pl.lotto.drawdategenerator;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

class DrawDateGenerator {

    private final DayOfWeek drawDayOfWeek;
    private final LocalTime drawTime;
    private final LocalDateTime drawDate;

    DrawDateGenerator(DayOfWeek drawDayOfWeek, LocalTime drawTime) {
        this.drawDayOfWeek = drawDayOfWeek;
        this.drawTime = drawTime;
    }

    void setNewDrawDateAndTime(DayOfWeek drawDayOfWeek, LocalTime drawTime) {
        return new DrawDateGenerator(drawDayOfWeek, drawTime, generateDrawDate(drawTime));

//
//        this.drawDayOfWeek = drawDayOfWeek;
//        this.drawTime = drawTime;
//        this.drawDate = generateDrawDate(this.drawDate));
        //TODO later: token database must be updated
        //TODO: should never be set to earlier date than previous date
    }

    LocalDateTime getDrawDate(LocalDateTime currentDateTime) {
        if (checkIfDrawDateIsValid()) {
            return drawDate;
        }
        this.drawDate = generateDrawDate(currentDateTime);
        return drawDate;
    }

    private LocalDateTime generateDrawDate(LocalDateTime currentDateTime, LocalTime drawTime) {
        LocalTime currentTime = currentDateTime.toLocalTime();
        LocalDate currentDate = currentDateTime.toLocalDate();
        DayOfWeek currentDayOfWeek = currentDateTime.getDayOfWeek();
        Duration daysToNextDraw = getDaysToNextDraw(currentTime, currentDayOfWeek);
        LocalDate expectedDrawDate = currentDate.plusDays(daysToNextDraw.toDays());
        return LocalDateTime.of(expectedDrawDate, drawTime);
    }

    private Duration getDaysToNextDraw(LocalTime currentTime, DayOfWeek currentDayOfWeek) {
        Duration daysToNextDraw;
        if (currentDayOfWeek == drawDayOfWeek && currentTime.isAfter(drawTime)) {
            daysToNextDraw = Duration.ofDays(DayOfWeek.values().length);
        } else {
            daysToNextDraw = getDurationBetweenDaysOfWeek(currentDayOfWeek, drawDayOfWeek);
        }
        return daysToNextDraw;
    }

    private Duration getDurationBetweenDaysOfWeek(DayOfWeek fromDay, DayOfWeek toDay) {
        int dayDifference = toDay.getValue() - fromDay.getValue();
        if (dayDifference < 0) {
            dayDifference = DayOfWeek.values().length + dayDifference;
        }
        return Duration.ofDays(dayDifference);
    }

    private boolean checkIfDrawDateIsValid() {
        if (Objects.isNull(drawDate)) {
            return false;
        }
        LocalDateTime currentDateTime = CurrentTimeGenerator.getCurrentDateAndTime();
        return currentDateTime.isBefore(drawDate);
    }
}
