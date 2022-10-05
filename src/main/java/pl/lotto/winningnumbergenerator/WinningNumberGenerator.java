package pl.lotto.winningnumbergenerator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

class WinningNumberGenerator {

    private final RandomNumbersGenerator randomNumbersGenerator;
    private final WinningUuidGenerator uuidGenerator;

    WinningNumberGenerator(RandomNumbersGenerator randomNumbersGenerator, WinningUuidGenerator uuidGenerator) {
        this.randomNumbersGenerator = randomNumbersGenerator;
        this.uuidGenerator = uuidGenerator;
    }

    WinningNumbers generateWinningNumbers(LocalDateTime drawDate) {
        List<Integer> generatedNumbers = randomNumbersGenerator.generateWinningNumbers();
        UUID uuid = uuidGenerator.generateRandomUUID();
        return new WinningNumbers(uuid, drawDate, generatedNumbers);
    }

}
