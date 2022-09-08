package pl.lotto.winningnumbergenerator.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public record WinningNumbersDto(LocalDateTime drawDate, List<Integer> winningNumbers) {

    @Override
    public List<Integer> winningNumbers() {
        return new ArrayList<>(winningNumbers);
    }

}
