package pl.lotto.winningnumbergenerator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.lotto.timegenerator.TimeGeneratorFacade;

@Configuration
public class WinningNumberGeneratorConfiguration {

    public WinningNumberGeneratorFacade createForTests(RandomGenerator randomGenerator, TimeGeneratorFacade timeGeneratorFacade,
                                                       WinningNumberRepository winningNumberRepository) {

        return createForProduction(randomGenerator, timeGeneratorFacade, winningNumberRepository);
    }

    @Bean("winningNumbersFacade")
    public WinningNumberGeneratorFacade createForProduction(RandomGenerator randomGenerator, TimeGeneratorFacade timeGeneratorFacade,
                                                            WinningNumberRepository winningNumberRepository) {
        WinningUuidGenerator uuidGenerator = new WinningUuidGenerator();
        WinningNumberGenerator winningNumberGenerator = new WinningNumberGenerator(randomGenerator, timeGeneratorFacade, uuidGenerator);
        return new WinningNumberGeneratorFacade(winningNumberGenerator, winningNumberRepository, timeGeneratorFacade);
    }

}
