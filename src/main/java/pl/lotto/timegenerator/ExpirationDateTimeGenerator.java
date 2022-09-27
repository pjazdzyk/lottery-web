package pl.lotto.timegenerator;

import java.time.Duration;
import java.time.LocalDateTime;

class ExpirationDateTimeGenerator {

    final private Duration expirationInDays;

    ExpirationDateTimeGenerator(Duration expirationInDays) {
        this.expirationInDays = expirationInDays;
    }

    LocalDateTime generateExpirationDate(LocalDateTime drawDateTime) {
        return drawDateTime.plusDays(expirationInDays.toDays());
    }

}
