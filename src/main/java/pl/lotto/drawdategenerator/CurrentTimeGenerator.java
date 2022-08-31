package pl.lotto.drawdategenerator;

import java.time.LocalDateTime;

class CurrentTimeGenerator {
    public static LocalDateTime getCurrentDateAndTime() {
        return LocalDateTime.now();
    }
}
