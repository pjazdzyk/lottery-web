package com.synerset.lottery.lotterygenerator.winningnumbergenerator;

import org.mockito.Mockito;

import java.util.List;

interface MockedRandomGenerator {

    default RandomNumbersGenerator getMockedNumberGenerator(List<Integer> overrideNumbers){
        RandomNumbersGenerator mockedRandomNumbersGenerator = Mockito.mock(RandomNumbersGenerator.class);
        Mockito.when(mockedRandomNumbersGenerator.generateWinningNumbers()).thenReturn(overrideNumbers);
        return mockedRandomNumbersGenerator;
    }

}
