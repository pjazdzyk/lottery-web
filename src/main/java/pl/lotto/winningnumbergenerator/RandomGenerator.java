package pl.lotto.winningnumbergenerator;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class RandomGenerator {

    private final int MIN_NUMBER_VALUE;
    private final int MAX_NUMBER_VALUE;
    private final int DRAWN_NUMBERS_COUNT;

    public RandomGenerator(int minNumberValue, int maxNumberValue, int drawnNumbersCount) {
        this.MIN_NUMBER_VALUE = minNumberValue;
        this.MAX_NUMBER_VALUE = maxNumberValue;
        this.DRAWN_NUMBERS_COUNT = drawnNumbersCount;
    }

    public List<Integer> generateWinningNumbers() {
        List<Integer> allValidNumbers = IntStream.rangeClosed(MIN_NUMBER_VALUE, MAX_NUMBER_VALUE).boxed().collect(Collectors.toList());
        Collections.shuffle(allValidNumbers);
        return allValidNumbers.stream().limit(DRAWN_NUMBERS_COUNT).toList();
    }

}
