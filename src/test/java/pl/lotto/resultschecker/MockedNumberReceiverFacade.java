package pl.lotto.resultschecker;

import pl.lotto.numberreceiver.NumberReceiverFacade;
import pl.lotto.numberreceiver.dto.InputStatus;
import pl.lotto.numberreceiver.dto.ReceiverDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

interface MockedNumberReceiverFacade extends SampleDrawDate{

    UUID sampleUuid = UUID.fromString("ef241277-64a2-457a-beea-a4c589803a26");
    LocalDateTime sampleCreationTime = LocalDateTime.of(2021, 8, 8, 12, 0, 0);
    LocalDateTime expirationDateTime = sampleDrawDateTime.plusYears(2);

    InputStatus saved = InputStatus.SAVED;

    List<ReceiverDto> sampleCouponList = List.of(
            // Winners
            new ReceiverDto(sampleUuid, sampleCreationTime, sampleDrawDateTime, expirationDateTime, List.of(1, 2, 3, 4, 5, 6), saved),
            new ReceiverDto(UUID.randomUUID(), sampleCreationTime, sampleDrawDateTime, expirationDateTime, List.of(1, 2, 3, 4, 50, 60), saved),
            new ReceiverDto(UUID.randomUUID(), sampleCreationTime, sampleDrawDateTime, expirationDateTime, List.of(1, 2, 3, 70, 80, 90), saved),
            // Losers
            new ReceiverDto(UUID.randomUUID(), sampleCreationTime, sampleDrawDateTime, expirationDateTime, List.of(1, 2, 30, 70, 80, 90), saved),
            new ReceiverDto(UUID.randomUUID(), sampleCreationTime, sampleDrawDateTime, expirationDateTime, List.of(10, 20, 30, 70, 80, 90), saved),
            new ReceiverDto(UUID.randomUUID(), sampleCreationTime, sampleDrawDateTime, expirationDateTime, List.of(10, 20, 30, 70, 80, 90), saved)
    );

    default NumberReceiverFacade createMockedNumberReceiverFacade() {
        NumberReceiverFacade mockedNumberReceiverFacade = mock(NumberReceiverFacade.class);
        when(mockedNumberReceiverFacade.getUserCouponByUUID(any(UUID.class))).thenReturn(sampleCouponList.get(0));
        when(mockedNumberReceiverFacade.getUserCouponListForDrawDate(any(LocalDateTime.class))).thenReturn(sampleCouponList);
        return mockedNumberReceiverFacade;
    }


}
