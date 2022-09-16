package pl.lotto.winningnumbergenerator;

import pl.lotto.winningnumbergenerator.dto.WinNumberStatus;
import pl.lotto.winningnumbergenerator.dto.WinningNumbersDto;

import java.util.List;
import java.util.stream.Collectors;

class WinningNumberMapper {

    public static WinningNumbersDto toDto(WinningNumbers winningNumbers, WinNumberStatus status) {
        return new WinningNumbersDto(winningNumbers.drawDate(), winningNumbers.winningNumbers(), status);
    }

    static List<WinningNumbersDto> toDtoList(List<WinningNumbers> winningNumbers, WinNumberStatus status) {
        return winningNumbers.stream()
                .map(winNums -> WinningNumberMapper.toDto(winNums, status))
                .collect(Collectors.toList());
    }

}
