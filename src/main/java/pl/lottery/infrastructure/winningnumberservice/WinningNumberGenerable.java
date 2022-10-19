package pl.lottery.infrastructure.winningnumberservice;

import pl.lottery.infrastructure.winningnumberservice.dto.WinningNumbersResponseDto;

import java.time.LocalDateTime;
import java.util.List;

public interface WinningNumberGenerable {
    WinningNumbersResponseDto generateWinningNumbers(LocalDateTime drawDate);

    WinningNumbersResponseDto retrieveWinningNumbersForDrawDate(LocalDateTime drawDate);

    WinningNumbersResponseDto deleteWinningNumbersForDate(LocalDateTime drawDate);

    List<WinningNumbersResponseDto> getAllWinningNumbers();
}
