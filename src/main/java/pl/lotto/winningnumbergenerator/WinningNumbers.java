package pl.lotto.winningnumbergenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

record WinningNumbers(LocalDateTime drawDate, List<Integer> winningNumbers) {

    @Override
    public List<Integer> winningNumbers() {
        return new ArrayList<>(winningNumbers);
    }

}
