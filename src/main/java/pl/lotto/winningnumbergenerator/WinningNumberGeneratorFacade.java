package pl.lotto.winningnumbergenerator;

import pl.lotto.timegenerator.TimeGeneratorFacade;
import pl.lotto.winningnumbergenerator.dto.WinNumberStatus;
import pl.lotto.winningnumbergenerator.dto.WinningNumbersDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class WinningNumberGeneratorFacade {

    private final WinningNumberGenerator winningNumberGenerator;
    private final WinningNumberRepository winningNumberRepository;
    private final TimeGeneratorFacade timeGeneratorFacade;

    public WinningNumberGeneratorFacade(WinningNumberGenerator winningNumberGenerator, WinningNumberRepository winningNumberRepository,
                                        TimeGeneratorFacade timeGeneratorFacade) {

        this.winningNumberGenerator = winningNumberGenerator;
        this.winningNumberRepository = winningNumberRepository;
        this.timeGeneratorFacade = timeGeneratorFacade;
    }

    //TODO implement call by scheduler
    public boolean runLottery() {
        if (checkIfActualNumbersAreAlreadyDrawnAndSaved()) {
            return false;
        }
        WinningNumbers winningNumbers = winningNumberGenerator.getWinningNumbers();
        winningNumberRepository.save(winningNumbers);
        return true;
    }

    public WinningNumbersDto getLastWinningNumbers() {
        LocalDateTime currentDrawDate = timeGeneratorFacade.getDrawDateAndTime();
        return getWinningNumbersForDate(currentDrawDate);
    }

    public WinningNumbersDto getWinningNumbersForDate(LocalDateTime drawDate) {
        Optional<WinningNumbers> winningNumbersForDrawDateOptional = winningNumberRepository.getWinningNumbersForDrawDate(drawDate);
        if (winningNumbersForDrawDateOptional.isEmpty()) {
            return notFoundDto();
        }
        return WinningNumberMapper.toDto(winningNumbersForDrawDateOptional.get(), WinNumberStatus.OK);
    }

    public WinningNumbersDto deleteWinningNumbersForDate(LocalDateTime drawDate) {
        Optional<WinningNumbers> winningNumbersForDrawDateOptional = winningNumberRepository.deleteWinningNumbersForDate(drawDate);
        if (winningNumbersForDrawDateOptional.isEmpty()) {
            return notFoundDto();
        }
        return WinningNumberMapper.toDto(winningNumbersForDrawDateOptional.get(), WinNumberStatus.DELETED);
    }

    public List<WinningNumbersDto> getAllWinningNumbers() {
        List<WinningNumbers> allWinningNumbers = winningNumberRepository.getAllWinningNumbers();
        return WinningNumberMapper.toDtoList(allWinningNumbers, WinNumberStatus.OK);
    }

    private boolean checkIfActualNumbersAreAlreadyDrawnAndSaved() {
        LocalDateTime drawDate = timeGeneratorFacade.getDrawDateAndTime();
        return winningNumberRepository.containsNumbersOfDrawDate(drawDate);
    }

    private WinningNumbersDto notFoundDto() {
        return new WinningNumbersDto(null, null, WinNumberStatus.NOT_FOUND);
    }

}
