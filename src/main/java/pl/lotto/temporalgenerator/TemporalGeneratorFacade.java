package pl.lotto.temporalgenerator;

import java.time.LocalDateTime;

public class TemporalGeneratorFacade {

    TemporalGenerator temporalGenerator;

    public TemporalGeneratorFacade(TemporalGenerator temporalGenerator) {
        this.temporalGenerator = temporalGenerator;
    }

    public LocalDateTime getDrawDateAndTime() {
        return temporalGenerator.getDrawDateAndTime();
    }

    public LocalDateTime getCurrentDateAndTime() {
        return temporalGenerator.getCurrentDateAndTime();
    }

    public LocalDateTime getExpirationDateAndTime(){
        return temporalGenerator.getExpirationDateTime();
    }

}

