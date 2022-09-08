package pl.lotto.winningnumbergenerator;

import pl.lotto.timegenerator.TimeGeneratorFacade;
import pl.lotto.winningnumbergenerator.dto.WinningNumbersDto;

import java.time.LocalDateTime;
import java.util.Optional;

public class WinningNumberGeneratorFacade {

    private final WinningNumberGenerator winningNumberGenerator;
    private final WinningNumberRepository winningNumberRepository;
    private final TimeGeneratorFacade timeGeneratorFacade;

    public WinningNumberGeneratorFacade(WinningNumberGenerator winningNumberGenerator, WinningNumberRepository winningNumberRepository, TimeGeneratorFacade timeGeneratorFacade) {
        this.winningNumberGenerator = winningNumberGenerator;
        this.winningNumberRepository = winningNumberRepository;
        this.timeGeneratorFacade = timeGeneratorFacade;
    }

    //TODO some mechanism must be responsible for invoking this method
    public boolean runLottery() {
        if (checkIfNumbersWasDrawnAndSaved()) {
            return false;
        }
        WinningNumbers winningNumbers = winningNumberGenerator.getWinningNumbers();
        winningNumberRepository.save(winningNumbers);
        return true;
    }

    public Optional<WinningNumbersDto> retrieveWinningNumbersForDate(LocalDateTime drawDate) {
        Optional<WinningNumbers> winningNumbersForDrawDate = winningNumberRepository.getWinningNumbersForDrawDate(drawDate);
        return winningNumbersForDrawDate.map(WinningNumberMapper::toDto);
    }

    public Optional<WinningNumbersDto> retrieveLAstWinningNumbers() {
        LocalDateTime currentDrawDate = timeGeneratorFacade.getDrawDateAndTime();
        return retrieveWinningNumbersForDate(currentDrawDate);
    }

    private boolean checkIfNumbersWasDrawnAndSaved() {
        LocalDateTime drawDate = timeGeneratorFacade.getDrawDateAndTime();
        return winningNumberRepository.containsNumbersOfDrawDate(drawDate);
    }


}
