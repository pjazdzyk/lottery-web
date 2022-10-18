package pl.lotto.infrastructure.winningnumberservice;

import pl.lotto.infrastructure.winningnumberservice.dto.WinningNumbersResponseDto;

import java.time.LocalDateTime;
import java.util.List;

public interface WinningNumberGenerable {
    WinningNumbersResponseDto generateWinningNumbers(LocalDateTime drawDate);

    WinningNumbersResponseDto retrieveWinningNumbersForDrawDate(LocalDateTime drawDate);

    WinningNumbersResponseDto deleteWinningNumbersForDate(LocalDateTime drawDate);

    List<WinningNumbersResponseDto> getAllWinningNumbers();
}
