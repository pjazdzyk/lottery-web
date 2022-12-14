package com.synerset.lottery.numberreceiver;

import com.synerset.lottery.timegenerator.TimeGeneratorFacade;

import java.time.LocalDateTime;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public interface MockedTimeGeneratorFacade {

    LocalDateTime sampleCurrentDateTime = LocalDateTime.of(2022, 8, 8, 10, 10);
    LocalDateTime sampleDrawDate = LocalDateTime.of(2022, 8, 12, 12, 0);
    LocalDateTime sampleExpirationDate = LocalDateTime.of(2024, 8, 12, 12, 0);

    default TimeGeneratorFacade createMockedTimeGeneratorFacade(LocalDateTime currentTime, LocalDateTime drawDate, LocalDateTime expirationDate) {
        TimeGeneratorFacade timeGeneratorFacade = mock(TimeGeneratorFacade.class);
        when(timeGeneratorFacade.retrieveCurrentDateAndTime()).thenReturn(currentTime);
        when(timeGeneratorFacade.retrieveNextDrawDateAndTime()).thenReturn(drawDate);
        when(timeGeneratorFacade.retrieveExpirationDateAndTime()).thenReturn(expirationDate);
        return timeGeneratorFacade;
    }

    default TimeGeneratorFacade createMockedTimeGeneratorFacadeWithDefaultDates() {
        return createMockedTimeGeneratorFacade(sampleCurrentDateTime, sampleDrawDate, sampleExpirationDate);
    }

    default void resetTimeFacadeToDefaultDates(TimeGeneratorFacade mockedTimeGeneratorFacade) {
        when(mockedTimeGeneratorFacade.retrieveCurrentDateAndTime()).thenReturn(sampleCurrentDateTime);
        when(mockedTimeGeneratorFacade.retrieveNextDrawDateAndTime()).thenReturn(sampleDrawDate);
        when(mockedTimeGeneratorFacade.retrieveExpirationDateAndTime()).thenReturn(sampleExpirationDate);
    }


}
