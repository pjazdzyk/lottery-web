package pl.lottery.timegenerator;
import java.time.LocalDateTime;

public class TimeGeneratorFacade {

    TimeGenerator timeGenerator;

    public TimeGeneratorFacade(TimeGenerator timeGenerator) {
        this.timeGenerator = timeGenerator;
    }

    public LocalDateTime retrieveNextDrawDateAndTime() {
        return timeGenerator.getDrawDateAndTime();
    }

    public LocalDateTime retrieveCurrentDateAndTime() {
        return timeGenerator.retrieveCurrentDateAndTime();
    }

    public LocalDateTime retrieveExpirationDateAndTime() {
        return timeGenerator.retrieveExpirationDateTime();
    }
}

