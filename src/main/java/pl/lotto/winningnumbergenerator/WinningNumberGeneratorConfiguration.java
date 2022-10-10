package pl.lotto.winningnumbergenerator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import pl.lotto.timegenerator.TimeGeneratorFacade;

@Configuration
public class WinningNumberGeneratorConfiguration {

    public WinningNumberGeneratorFacade createForTests(RandomNumbersGenerator randomNumbersGenerator, TimeGeneratorFacade timeGeneratorFacade,
                                                       WinningNumberRepository winningNumberRepository) {

        return createForProduction(randomNumbersGenerator, timeGeneratorFacade, winningNumberRepository);
    }

    @Bean("winningNumbersFacade")
    public WinningNumberGeneratorFacade createForProduction(RandomNumbersGenerator randomNumbersGenerator, TimeGeneratorFacade timeGeneratorFacade,
                                                            WinningNumberRepository winningNumberRepository) {
        WinningUuidGenerator uuidGenerator = new WinningUuidGenerator();
        WinningNumberGenerator winningNumberGenerator = new WinningNumberGenerator(randomNumbersGenerator, uuidGenerator);
        return new WinningNumberGeneratorFacade(winningNumberGenerator, winningNumberRepository, timeGeneratorFacade);
    }

    @Bean
    @Profile("production")
    RandomNumbersGenerator createRandomNumberGenerator(WinningPropertyConfigurable propertyConfig){
        return new RandomNumbersGenerator(propertyConfig);
    }

}
