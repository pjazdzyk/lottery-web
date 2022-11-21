package pl.lottery.resultschecker;

import pl.lottery.infrastructure.winningnumberservice.dto.WinningNumbersResponseDto;

import java.time.LocalDateTime;
import java.util.List;

public interface WinningNumberGeneratorPort {
    WinningNumbersResponseDto generateWinningNumbers(LocalDateTime drawDate);

    WinningNumbersResponseDto retrieveWinningNumbers(LocalDateTime drawDate);

}
