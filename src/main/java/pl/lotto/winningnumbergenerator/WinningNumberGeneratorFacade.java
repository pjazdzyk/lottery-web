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

    public boolean generateWinningNumbers(LocalDateTime drawDate) {
        if (checkIfActualNumbersAreAlreadyDrawnAndSaved(drawDate)) {
            return false;
        }
        WinningNumbers winningNumbers = winningNumberGenerator.generateWinningNumbers(drawDate);
        winningNumberRepository.save(winningNumbers);
        return true;
    }

    public WinningNumbersDto getWinningNumbersForDate(LocalDateTime drawDate) {
        Optional<WinningNumbers> winningNumbersForDrawDateOptional = winningNumberRepository.findByDrawDate(drawDate);
        if (winningNumbersForDrawDateOptional.isEmpty()) {
            return notFoundDto();
        }
        return WinningNumberMapper.toDto(winningNumbersForDrawDateOptional.get(), WinNumberStatus.SAVED);
    }

    public WinningNumbersDto deleteWinningNumbersForDate(LocalDateTime drawDate) {
        Optional<WinningNumbers> winningNumbersForDrawDateOptional = winningNumberRepository.findByDrawDate(drawDate);
        if (winningNumbersForDrawDateOptional.isEmpty()) {
            return notFoundDto();
        }
        winningNumberRepository.deleteByDrawDate(drawDate);
        return WinningNumberMapper.toDto(winningNumbersForDrawDateOptional.get(), WinNumberStatus.DELETED);
    }

    public List<WinningNumbersDto> getAllWinningNumbers() {
        List<WinningNumbers> allWinningNumbers = winningNumberRepository.findAll();
        return WinningNumberMapper.toDtoList(allWinningNumbers, WinNumberStatus.SAVED);
    }

    private boolean checkIfActualNumbersAreAlreadyDrawnAndSaved(LocalDateTime drawDate) {
        return winningNumberRepository.existsByDrawDate(drawDate);
    }

    private WinningNumbersDto notFoundDto() {
        return new WinningNumbersDto(null, null, WinNumberStatus.NOT_FOUND);
    }

}
