package com.synerset.lottery.lotterygenerator.winningnumbergenerator;

import com.synerset.lottery.lotterygenerator.winningnumbergenerator.dto.WinningNumberStatus;
import com.synerset.lottery.lotterygenerator.winningnumbergenerator.dto.WinningNumbersResponseDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

class WinningNumberGeneratorFacadeImpl implements WinningNumberGeneratorFacade {

    private final WinningNumberGenerator winningNumberGenerator;
    private final WinningNumberRepository winningNumberRepository;

    public WinningNumberGeneratorFacadeImpl(WinningNumberGenerator winningNumberGenerator, WinningNumberRepository winningNumberRepository) {
        this.winningNumberGenerator = winningNumberGenerator;
        this.winningNumberRepository = winningNumberRepository;

    }

    @Override
    public WinningNumbersResponseDto generateWinningNumbers(LocalDateTime drawDate) {
        if (checkIfNumbersForThatDateExists(drawDate)) {
            return new WinningNumbersResponseDto(drawDate, null, WinningNumberStatus.ALREADY_EXISTS);
        }
        WinningNumbers winningNumbers = winningNumberGenerator.generateWinningNumbers(drawDate);
        winningNumberRepository.save(winningNumbers);
        return WinningNumberMapper.toDto(winningNumbers, WinningNumberStatus.GENERATED);
    }

    @Override
    public WinningNumbersResponseDto retrieveWinningNumbersForDrawDate(LocalDateTime drawDate) {
        Optional<WinningNumbers> winningNumbersForDrawDateOptional = winningNumberRepository.findByDrawDate(drawDate);
        if (winningNumbersForDrawDateOptional.isEmpty()) {
            return notFoundDto(drawDate);
        }
        return WinningNumberMapper.toDto(winningNumbersForDrawDateOptional.get(), WinningNumberStatus.LOADED_FROM_DB);
    }

    @Override
    public WinningNumbersResponseDto deleteWinningNumbersForDate(LocalDateTime drawDate) {
        Optional<WinningNumbers> winningNumbersForDrawDateOptional = winningNumberRepository.findByDrawDate(drawDate);
        if (winningNumbersForDrawDateOptional.isEmpty()) {
            return notFoundDto(drawDate);
        }
        winningNumberRepository.deleteByDrawDate(drawDate);
        return WinningNumberMapper.toDto(winningNumbersForDrawDateOptional.get(), WinningNumberStatus.DELETED);
    }

    @Override
    public List<WinningNumbersResponseDto> getAllWinningNumbers() {
        List<WinningNumbers> allWinningNumbers = winningNumberRepository.findAll();
        return WinningNumberMapper.toDtoList(allWinningNumbers, WinningNumberStatus.LOADED_FROM_DB);
    }

    private boolean checkIfNumbersForThatDateExists(LocalDateTime drawDate) {
        return winningNumberRepository.existsByDrawDate(drawDate);
    }

    private WinningNumbersResponseDto notFoundDto(LocalDateTime drawDate) {
        return new WinningNumbersResponseDto(drawDate, null, WinningNumberStatus.NOT_FOUND);
    }

}
