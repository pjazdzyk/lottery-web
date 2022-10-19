package pl.lottery.numberreceiver;

import pl.lottery.timegenerator.TimeGeneratorFacade;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

public interface MockedTimeGeneratorFacade {

    LocalDateTime sampleCurrentDateTime = LocalDateTime.of(2022, 8, 8, 10, 10);
    LocalDateTime sampleDrawDate = LocalDateTime.of(2022, 8, 12, 12, 0);
    LocalDateTime sampleExpirationDate = LocalDateTime.of(2024, 8, 12, 12, 0);

    default TimeGeneratorFacade createMockedTimeGeneratorFacade(LocalDateTime currentTime, LocalDateTime drawDate, LocalDateTime expirationDate) {
        TimeGeneratorFacade timeGeneratorFacade = mock(TimeGeneratorFacade.class);
        when(timeGeneratorFacade.getCurrentDateAndTime()).thenReturn(currentTime);
        when(timeGeneratorFacade.getDrawDateAndTime()).thenReturn(drawDate);
        when(timeGeneratorFacade.getExpirationDateAndTime()).thenReturn(expirationDate);
        return timeGeneratorFacade;
    }

    default TimeGeneratorFacade createMockedTimeGeneratorFacadeWithDefaultDates() {
        return createMockedTimeGeneratorFacade(sampleCurrentDateTime, sampleDrawDate, sampleExpirationDate);
    }

    default void resetTimeFacadeToDefaultDates(TimeGeneratorFacade mockedTimeGeneratorFacade) {
        when(mockedTimeGeneratorFacade.getCurrentDateAndTime()).thenReturn(sampleCurrentDateTime);
        when(mockedTimeGeneratorFacade.getDrawDateAndTime()).thenReturn(sampleDrawDate);
        when(mockedTimeGeneratorFacade.getExpirationDateAndTime()).thenReturn(sampleExpirationDate);
    }


}
