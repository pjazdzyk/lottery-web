package pl.lotto.timegenerator;

import java.time.Clock;
import java.time.LocalDateTime;

class CurrentDateTimeGenerator {

    private final Clock clock;

    CurrentDateTimeGenerator(Clock clock) {
        this.clock = clock;
    }

    public LocalDateTime getCurrentDateAndTime() {
        return LocalDateTime.now(clock);
    }
}
