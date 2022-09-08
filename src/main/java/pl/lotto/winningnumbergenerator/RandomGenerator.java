package pl.lotto.winningnumbergenerator;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class RandomGenerator {

    private final byte MIN_NUMBER_VALUE = 1;
    private final byte MAX_NUMBER_VALUE = 99;
    private final byte NUMBERS_TO_DRAW = 6;

    public List<Integer> generateWinningNumbers() {
        List<Integer> allValidNumbers = IntStream.rangeClosed(MIN_NUMBER_VALUE, MAX_NUMBER_VALUE).boxed().collect(Collectors.toList());
        Collections.shuffle(allValidNumbers);
        return allValidNumbers.stream().limit(NUMBERS_TO_DRAW).toList();
    }

}
