package pl.lotto.timegenerator;

import java.time.LocalDateTime;

class TimeGenerator {

    private final CurrentDateTimeGenerator currentDateTimeGenerator;
    private final DrawDateTimeGenerator drawDateTimeGenerator;

    private final ExpirationDateTimeGenerator expirationDateTimeGenerator;
    private LocalDateTime drawDate; //TODO cant set as final, as once a week this will change

    TimeGenerator(CurrentDateTimeGenerator currentDateTimeGenerator, DrawDateTimeGenerator drawDateTimeGenerator, ExpirationDateTimeGenerator expirationDateTimeGenerator) {
        this.currentDateTimeGenerator = currentDateTimeGenerator;
        this.drawDateTimeGenerator = drawDateTimeGenerator;
        this.expirationDateTimeGenerator = expirationDateTimeGenerator;
        this.drawDate = drawDateTimeGenerator.generateDrawDate(currentDateTimeGenerator.getCurrentDateAndTime());
    }

    LocalDateTime getCurrentDateAndTime() {
        return currentDateTimeGenerator.getCurrentDateAndTime();
    }

    LocalDateTime getDrawDateAndTime() {
        if (checkIfDrawDateIsOutdated()) {
            LocalDateTime timeNow = currentDateTimeGenerator.getCurrentDateAndTime();
            this.drawDate = drawDateTimeGenerator.generateDrawDate(timeNow);
        }
        return drawDate;
    }

    LocalDateTime getExpirationDateTime() {
        return expirationDateTimeGenerator.generateExpirationDate(getDrawDateAndTime());
    }

    private boolean checkIfDrawDateIsOutdated() {
        LocalDateTime timeNow = currentDateTimeGenerator.getCurrentDateAndTime();
        return timeNow.isAfter(drawDate);
    }

}
