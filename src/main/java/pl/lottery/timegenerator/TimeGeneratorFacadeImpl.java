package pl.lottery.timegenerator;
import java.time.LocalDateTime;

class TimeGeneratorFacadeImpl implements TimeGeneratorFacade {

    private final TimeGenerator timeGenerator;

    public TimeGeneratorFacadeImpl(TimeGenerator timeGenerator) {
        this.timeGenerator = timeGenerator;
    }

    @Override
    public LocalDateTime retrieveNextDrawDateAndTime() {
        return timeGenerator.getDrawDateAndTime();
    }

    @Override
    public LocalDateTime retrieveCurrentDateAndTime() {
        return timeGenerator.retrieveCurrentDateAndTime();
    }

    @Override
    public LocalDateTime retrieveExpirationDateAndTime() {
        return timeGenerator.retrieveExpirationDateTime();
    }
}

