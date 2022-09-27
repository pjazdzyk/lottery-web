package pl.lotto.winningnumbergenerator;

import pl.lotto.timegenerator.TimeGeneratorFacade;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

class WinningNumberGenerator {

    private final RandomGenerator randomGenerator;
    private final TimeGeneratorFacade timeGenerator;
    private final WinningUuidGenerator uuidGenerator;
    private WinningNumbers winningNumbers;

    WinningNumberGenerator(RandomGenerator randomGenerator, TimeGeneratorFacade timeGenerator, WinningUuidGenerator uuidGenerator) {
        this.randomGenerator = randomGenerator;
        this.timeGenerator = timeGenerator;
        this.uuidGenerator = uuidGenerator;
    }

    WinningNumbers getWinningNumbers() {
        if (!checkIfCurrentNumbersAreValid()) {
            LocalDateTime drawDate = timeGenerator.getDrawDateAndTime();
            List<Integer> generatedNumbers = randomGenerator.generateWinningNumbers();
            UUID uuid = uuidGenerator.generateRandomUUID();
            winningNumbers = new WinningNumbers(uuid, drawDate, generatedNumbers);
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
