package pl.lotto.resultschecker;

import pl.lotto.winningnumbergenerator.WinningNumberGeneratorFacade;
import pl.lotto.winningnumbergenerator.dto.WinningNumbersDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

interface MockedWinningNumberGeneratorFacade extends SampleDrawDate {

    List<Integer> sampleWinningNumbers = List.of(1, 2, 3, 4, 5, 6);
    WinningNumbersDto winningNumbersDto = new WinningNumbersDto(sampleDrawDateTime, sampleWinningNumbers);

    default WinningNumberGeneratorFacade createWinningNumberFacade() {
        WinningNumberGeneratorFacade winningNumberGeneratorFacade = mock(WinningNumberGeneratorFacade.class);
        when(winningNumberGeneratorFacade.getWinningNumbersForDate(any(LocalDateTime.class))).thenReturn(Optional.of(winningNumbersDto));
        when(winningNumberGeneratorFacade.getLastWinningNumbers()).thenReturn(Optional.of(winningNumbersDto));
        return winningNumberGeneratorFacade;
    }

}
