package com.synerset.lottery.lotterygenerator.winningnumbergenerator.dto;

import java.time.LocalDateTime;
import java.util.List;

public record WinningNumbersResponseDto(LocalDateTime drawDate,
                                        List<Integer> winningNumbers,
                                        WinningNumberStatus status) {
}