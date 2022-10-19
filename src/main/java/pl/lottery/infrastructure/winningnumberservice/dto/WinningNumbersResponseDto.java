package pl.lottery.infrastructure.winningnumberservice.dto;

import java.time.LocalDateTime;
import java.util.List;

public record WinningNumbersResponseDto(LocalDateTime drawDate,
                                        List<Integer> winningNumbers,
                                        WinningNumberStatus status) {
}