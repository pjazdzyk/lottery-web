package pl.lottery.resultschecker;

import pl.lottery.infrastructure.winningnumberservice.WinningNumberGenerable;
import pl.lottery.infrastructure.winningnumberservice.dto.WinningNumberStatus;
import pl.lottery.infrastructure.winningnumberservice.dto.WinningNumbersResponseDto;

import java.time.LocalDateTime;
import java.util.List;

public class WinningNumbersServiceStub implements WinningNumberGenerable {

    private final List<Integer> winningNumbers;

    public WinningNumbersServiceStub(List<Integer> winningNumbers) {
        this.winningNumbers = winningNumbers;
    }

    @Override
    public WinningNumbersResponseDto generateWinningNumbers(LocalDateTime drawDate) {
        return new WinningNumbersResponseDto(drawDate, winningNumbers, WinningNumberStatus.GENERATED);
    }

    @Override
    public WinningNumbersResponseDto retrieveWinningNumbersForDrawDate(LocalDateTime drawDate) {
        return new WinningNumbersResponseDto(drawDate, winningNumbers, WinningNumberStatus.LOADED_FROM_DB);
    }

    @Override
    public WinningNumbersResponseDto deleteWinningNumbersForDate(LocalDateTime drawDate) {
        return new WinningNumbersResponseDto(drawDate, null, WinningNumberStatus.DELETED);
    }

    @Override
    public List<WinningNumbersResponseDto> getAllWinningNumbers() {
        return null;
    }
}
