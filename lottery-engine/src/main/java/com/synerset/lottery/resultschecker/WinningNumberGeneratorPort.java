package com.synerset.lottery.resultschecker;

import com.synerset.lottery.infrastructure.winningnumberservice.dto.WinningNumbersResponseDto;

import java.time.LocalDateTime;

public interface WinningNumberGeneratorPort {
    WinningNumbersResponseDto generateWinningNumbers(LocalDateTime drawDate);

    WinningNumbersResponseDto retrieveWinningNumbers(LocalDateTime drawDate);

}
