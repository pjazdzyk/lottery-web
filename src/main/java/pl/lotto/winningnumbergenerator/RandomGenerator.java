package pl.lotto.winningnumbergenerator;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class RandomGenerator {

    private final RandomGeneratorRules randomGeneratorRules;

    public RandomGenerator(RandomGeneratorRules randomGeneratorRules) {
        this.randomGeneratorRules = randomGeneratorRules;
    }

    public List<Integer> generateWinningNumbers() {
        List<Integer> allValidNumbers = IntStream.rangeClosed(randomGeneratorRules.minNumberValue(),
                randomGeneratorRules.maxNumberValue()).boxed().collect(Collectors.toList());

        Collections.shuffle(allValidNumbers);
        return allValidNumbers.stream().limit(randomGeneratorRules.drawnNumbersCount()).toList();
    }

}
