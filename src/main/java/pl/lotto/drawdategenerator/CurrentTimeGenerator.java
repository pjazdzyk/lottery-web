package pl.lotto.drawdategenerator;

import java.time.Clock;
import java.time.LocalDateTime;

class CurrentTimeGenerator {
    Clock clock;

    CurrentTimeGenerator(Clock clock) {
        this.clock = clock;
    }

    public LocalDateTime getCurrentDateAndTime() {
        return LocalDateTime.now(clock);
    }
}
