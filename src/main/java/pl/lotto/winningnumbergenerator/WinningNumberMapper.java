package pl.lotto.winningnumbergenerator;

import pl.lotto.winningnumbergenerator.dto.WinningNumbersDto;

class WinningNumberMapper {
    public static WinningNumbers toWinningNumbers(WinningNumbersDto winningNumbersDto) {
        return new WinningNumbers(winningNumbersDto.drawDate(), winningNumbersDto.winningNumbers());
    }

    public static WinningNumbersDto toDto(WinningNumbers winningNumbers) {
        return new WinningNumbersDto(winningNumbers.drawDate(), winningNumbers.winningNumbers());
    }
}
