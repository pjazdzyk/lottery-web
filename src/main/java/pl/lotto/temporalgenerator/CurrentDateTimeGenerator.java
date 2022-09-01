package pl.lotto.temporalgenerator;

import java.time.Clock;
import java.time.LocalDateTime;

class CurrentDateTimeGenerator {
    Clock clock;

    CurrentDateTimeGenerator(Clock clock) {
        this.clock = clock;
    }

    public LocalDateTime getCurrentDateAndTime() {
        return LocalDateTime.now(clock);
    }
}
