package pl.lotto.winningnumbergenerator;

import pl.lotto.timegenerator.TimeGeneratorFacade;
import pl.lotto.winningnumbergenerator.dto.WinningNumbersDto;

import java.time.LocalDateTime;
import java.util.List;
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

    //TODO some mechanism must be responsible for invoking this method exactly at drawTIme
    public boolean runLottery() {
        if (checkIfActualNumbersAreAlreadyDrawnAndSaved()) {
            return false;
        }
        WinningNumbers winningNumbers = winningNumberGenerator.getWinningNumbers();
        winningNumberRepository.save(winningNumbers);
        return true;
    }

    public Optional<WinningNumbersDto> retrieveLastWinningNumbers() {
        LocalDateTime currentDrawDate = timeGeneratorFacade.getDrawDateAndTime();
        return retrieveWinningNumbersForDate(currentDrawDate);
    }

    public Optional<WinningNumbersDto> retrieveWinningNumbersForDate(LocalDateTime drawDate) {
        Optional<WinningNumbers> winningNumbersForDrawDate = winningNumberRepository.getWinningNumbersForDrawDate(drawDate);
        return winningNumbersForDrawDate.map(WinningNumberMapper::toDto);
    }

    public Optional<WinningNumbersDto> deleteWinningNumbersForDate(LocalDateTime drawDate){
        Optional<WinningNumbers> winningNumbersForDrawDate = winningNumberRepository.deleteWinningNumbersForDate(drawDate);
        return winningNumbersForDrawDate.map(WinningNumberMapper::toDto);
    }

    public List<WinningNumbersDto> getAllWinningNumbers(){
        List<WinningNumbers> allWinningNumbers = winningNumberRepository.getAllWinningNumbers();
        return WinningNumberMapper.toDtoList(allWinningNumbers);
    }

    private boolean checkIfActualNumbersAreAlreadyDrawnAndSaved() {
        LocalDateTime drawDate = timeGeneratorFacade.getDrawDateAndTime();
        return winningNumberRepository.containsNumbersOfDrawDate(drawDate);
    }

}
