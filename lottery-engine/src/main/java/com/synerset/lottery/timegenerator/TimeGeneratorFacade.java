package com.synerset.lottery.timegenerator;

import java.time.LocalDateTime;

public interface TimeGeneratorFacade {
    LocalDateTime retrieveNextDrawDateAndTime();

    LocalDateTime retrieveCurrentDateAndTime();

    LocalDateTime retrieveExpirationDateAndTime();
}
