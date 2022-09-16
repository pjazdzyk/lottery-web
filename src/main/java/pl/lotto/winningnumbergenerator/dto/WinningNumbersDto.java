package pl.lotto.winningnumbergenerator.dto;

import java.time.LocalDateTime;
import java.util.List;

public record WinningNumbersDto(LocalDateTime drawDate, List<Integer> winningNumbers, WinNumberStatus status) {
}