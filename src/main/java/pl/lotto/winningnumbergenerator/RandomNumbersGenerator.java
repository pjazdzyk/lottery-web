package pl.lotto.winningnumbergenerator;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
class RandomNumbersGenerator {

    private final WinningPropertyConfigurable winningNumbersConfig;

    public RandomNumbersGenerator(WinningPropertyConfigurable winningNumbersConfig) {
        this.winningNumbersConfig = winningNumbersConfig;
    }

    public List<Integer> generateWinningNumbers() {
        List<Integer> allValidNumbers = IntStream.rangeClosed(winningNumbersConfig.getMinWinNumber(),
                winningNumbersConfig.getMaxWinNumber()).boxed().collect(Collectors.toList());

        Collections.shuffle(allValidNumbers);
        return allValidNumbers.stream().limit(winningNumbersConfig.getWinNumberCount()).toList();
    }

}
