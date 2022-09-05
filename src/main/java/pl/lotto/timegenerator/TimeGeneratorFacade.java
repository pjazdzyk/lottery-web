package pl.lotto.timegenerator;

import java.time.LocalDateTime;

public class TimeGeneratorFacade {

    TimeGenerator timeGenerator;

    public TimeGeneratorFacade(TimeGenerator timeGenerator) {
        this.timeGenerator = timeGenerator;
    }

    public LocalDateTime getDrawDateAndTime() {
        return timeGenerator.getDrawDateAndTime();
    }

    public LocalDateTime getCurrentDateAndTime() {
        return timeGenerator.getCurrentDateAndTime();
    }

    public LocalDateTime getExpirationDateAndTime() {
        return timeGenerator.getExpirationDateTime();
    }

}

