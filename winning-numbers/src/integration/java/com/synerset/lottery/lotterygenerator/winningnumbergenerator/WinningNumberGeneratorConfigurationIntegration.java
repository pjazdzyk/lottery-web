package com.synerset.lottery.lotterygenerator.winningnumbergenerator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
@Profile("integration")
public class WinningNumberGeneratorConfigurationIntegration implements MockedRandomGenerator {

    private final List<Integer> winningNumbers = List.of(1, 2, 3, 4, 5, 6);

    @Bean
    RandomNumbersGenerator createRandomNumberGenerator() {
        return getMockedNumberGenerator(winningNumbers);
    }

}
