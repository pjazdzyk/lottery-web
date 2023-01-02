package com.synerset.lottery.timegenerator;

import java.time.*;

public class ProgressingAdjustableClock extends AdjustableClock {

    private final Instant creationPointInTime;

    ProgressingAdjustableClock(Instant initialInstant, ZoneId zone) {
        super(initialInstant, zone);
        this.creationPointInTime = Instant.now();
    }

    @Override
    public Instant instant() {
        Instant currentPointInTime = Instant.now();
        Duration timeAdvanced = getTimeDifference(creationPointInTime, currentPointInTime);
        return instant.plus(timeAdvanced);
    }

    private Duration getTimeDifference(Instant firstPointInTime, Instant secondPointInTime) {
        Duration difference = Duration.between(firstPointInTime, secondPointInTime);
        if (difference.isNegative())
            return Duration.ZERO;
        return difference;
    }

    public static ProgressingAdjustableClock ofLocalDateAndLocalTime(LocalDate date, LocalTime time, ZoneId zone) {
        ZonedDateTime zoneDateTime = createZoneDateTime(date, time, zone);
        return new ProgressingAdjustableClock(zoneDateTime.toInstant(), zone);
    }

}
