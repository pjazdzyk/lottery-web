package pl.lotto.winningnumbergenerator;

import pl.lotto.timegenerator.TimeGeneratorFacade;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

class WinningNumberGenerator {

    private final RandomGenerator randomGenerator;
    private final TimeGeneratorFacade timeGenerator;
    private WinningNumbers winningNumbers;

    WinningNumberGenerator(RandomGenerator randomGenerator, TimeGeneratorFacade timeGenerator) {
        this.randomGenerator = randomGenerator;
        this.timeGenerator = timeGenerator;
    }

    WinningNumbers getWinningNumbers() {
        if (!checkIfCurrentNumbersAreValid()) {
            LocalDateTime drawDate = timeGenerator.getDrawDateAndTime();
            List<Integer> generatedNumbers = randomGenerator.generateWinningNumbers();
            winningNumbers = new WinningNumbers(drawDate, generatedNumbers);
        }
        return winningNumbers;
    }

    private boolean checkIfCurrentNumbersAreValid() {
        if (Objects.isNull(winningNumbers)) {
            return false;
        }
        return timeGenerator.getCurrentDateAndTime().isBefore(winningNumbers.drawDate());
    }


}
