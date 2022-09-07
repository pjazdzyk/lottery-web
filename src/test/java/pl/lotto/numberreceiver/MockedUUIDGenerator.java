package pl.lotto.numberreceiver;

import org.mockito.Mockito;

import java.util.UUID;

public interface MockedUUIDGenerator {

    default UUIDGenerator getMockedUUIDGenerator(UUID uuid) {
        UUIDGenerator mockedUuidGenerator = Mockito.mock(UUIDGenerator.class);
        Mockito.when(mockedUuidGenerator.generateRandomUUID()).thenReturn(uuid);
        return mockedUuidGenerator;
    }

}
