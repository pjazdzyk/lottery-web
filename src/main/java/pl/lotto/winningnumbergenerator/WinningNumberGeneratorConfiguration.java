package pl.lotto.winningnumbergenerator;

import pl.lotto.timegenerator.TimeGeneratorFacade;

public class WinningNumberGeneratorConfiguration {
    public WinningNumberGeneratorFacade createForTests(RandomGenerator randomGenerator, TimeGeneratorFacade timeGeneratorFacade, WinningNumberRepository winningNumberRepository) {
        WinningNumberGenerator winningNumberGenerator = new WinningNumberGenerator(randomGenerator, timeGeneratorFacade);
        return new WinningNumberGeneratorFacade(winningNumberGenerator, winningNumberRepository, timeGeneratorFacade);
    }
}
