package pl.lotto.winningnumbergenerator;

import org.mockito.Mockito;

import java.util.List;

interface MockedNumberGenerator {

    default RandomGenerator getMockedNumberGenerator(List<Integer> overrideNumbers){
        RandomGenerator mockedRandomGenerator = Mockito.mock(RandomGenerator.class);
        Mockito.when(mockedRandomGenerator.generateWinningNumbers()).thenReturn(overrideNumbers);
        return mockedRandomGenerator;
    }

}
