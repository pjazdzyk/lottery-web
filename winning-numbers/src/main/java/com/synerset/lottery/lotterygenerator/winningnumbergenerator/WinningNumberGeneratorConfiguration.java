package com.synerset.lottery.lotterygenerator.winningnumbergenerator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
class WinningNumberGeneratorConfiguration {

    public WinningNumberGeneratorFacade createForTests(RandomNumbersGenerator randomNumbersGenerator, WinningNumberRepository winningNumberRepository) {

        return createForProduction(randomNumbersGenerator, winningNumberRepository);
    }

    @Bean("winningNumbersFacade")
    public WinningNumberGeneratorFacade createForProduction(RandomNumbersGenerator randomNumbersGenerator, WinningNumberRepository winningNumberRepository) {
        WinningUuidGenerator uuidGenerator = new WinningUuidGenerator();
        WinningNumberGenerator winningNumberGenerator = new WinningNumberGenerator(randomNumbersGenerator, uuidGenerator);
        return new WinningNumberGeneratorFacadeImpl(winningNumberGenerator, winningNumberRepository);
    }

    @Bean
    public ObjectMapper createObjectMapperWithModules(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }

    @Bean
    @Profile({"production","dev"})
    RandomNumbersGenerator createRandomNumberGenerator(WinningPropertyConfigurable propertyConfig){
        return new RandomNumbersGenerator(propertyConfig);
    }




}
