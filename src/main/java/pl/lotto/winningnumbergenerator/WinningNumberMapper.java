package pl.lotto.winningnumbergenerator;

import pl.lotto.winningnumbergenerator.dto.WinningNumbersDto;

import java.util.List;
import java.util.stream.Collectors;

class WinningNumberMapper {
    public static WinningNumbers toWinningNumbers(WinningNumbersDto winningNumbersDto) {
        return new WinningNumbers(winningNumbersDto.drawDate(), winningNumbersDto.winningNumbers());
    }

    public static WinningNumbersDto toDto(WinningNumbers winningNumbers) {
        return new WinningNumbersDto(winningNumbers.drawDate(), winningNumbers.winningNumbers());
    }

    static List<WinningNumbersDto> toDtoList(List<WinningNumbers> winningNumbers) {
        return winningNumbers.stream()
                .map(WinningNumberMapper::toDto)
                .collect(Collectors.toList());
    }

    static List<WinningNumbers> toWinningNumbersList(List<WinningNumbersDto> couponDto) {
        return couponDto.stream()
                .map(WinningNumberMapper::toWinningNumbers)
                .collect(Collectors.toList());
    }


}
