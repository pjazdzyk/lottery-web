package pl.lotto.winningnumbergenerator;

import org.mockito.Mockito;

import java.util.List;

interface MockedNumberGenerator {

    NumberGenerator getMockedNumberGenerator(List<Integer> overridedNumbers){
        NumberGenerator mockedNumberGenerator = Mockito.mock(NumberGenerator.class);
        Mockito.when(mockedNumberGenerator.generateWinnningNumbers()).thenReturn(overridedNumbers);
        return mockedNumberGenerator;
    }

}
