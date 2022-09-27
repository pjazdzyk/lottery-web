package pl.lotto.numberreceiver;

import org.mockito.Mockito;

import java.util.UUID;

interface MockedUUIDGenerator {

    default ReceiverUuidGenerator getMockedUUIDGenerator(UUID uuid) {
        ReceiverUuidGenerator mockedReceiverUuidGenerator = Mockito.mock(ReceiverUuidGenerator.class);
        Mockito.when(mockedReceiverUuidGenerator.generateRandomUUID()).thenReturn(uuid);
        return mockedReceiverUuidGenerator;
    }

}
