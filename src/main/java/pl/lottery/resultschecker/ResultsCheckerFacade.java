package pl.lottery.resultschecker;

import pl.lottery.infrastructure.winningnumberservice.dto.WinningNumberStatus;
import pl.lottery.infrastructure.winningnumberservice.dto.WinningNumbersResponseDto;
import pl.lottery.numberreceiver.NumberReceiverFacade;
import pl.lottery.numberreceiver.dto.ReceiverResponseDto;
import pl.lottery.resultschecker.dto.CheckerDto;
import pl.lottery.resultschecker.dto.CheckerStatus;
import pl.lottery.resultschecker.exceptions.WinningNumbersServiceFailureException;

import java.time.LocalDateTime;
import java.util.*;

public class ResultsCheckerFacade {
    private final NumberReceiverFacade numberReceiverFacade;
    private final WinningNumberGeneratorPort winningNumbersService;
    private final LotteryResultsGenerator lotteryResultsGenerator;
    private final ResultsCheckerRepository resultsCheckerRepository;

    public ResultsCheckerFacade(NumberReceiverFacade numberReceiverFacade, WinningNumberGeneratorPort winningNumbersService,
                                LotteryResultsGenerator lotteryResultsGenerator, ResultsCheckerRepository resultsCheckerRepository) {
        this.numberReceiverFacade = numberReceiverFacade;
        this.winningNumbersService = winningNumbersService;
        this.lotteryResultsGenerator = lotteryResultsGenerator;
        this.resultsCheckerRepository = resultsCheckerRepository;
    }

    public int generateLotteryResultsForDrawDate(LocalDateTime drawDate) {
        boolean resultsExistsForThisDrawDate = containsResultsForDrawDate(drawDate);
        if (resultsExistsForThisDrawDate) {
            return 0;
        }
        List<Integer> winningNumbersForThisDrawDate = retrieveWinningNumbers(drawDate);
        List<ReceiverResponseDto> couponsForThisDrawDate = numberReceiverFacade.getUserCouponListForDrawDate(drawDate);
        List<LotteryResults> processedLotteryResults = lotteryResultsGenerator.generateLotteryResultsList(couponsForThisDrawDate, winningNumbersForThisDrawDate);
        resultsCheckerRepository.saveAll(processedLotteryResults);
        return processedLotteryResults.size();
    }

    public CheckerDto getResultsForId(UUID uuid) {
        Optional<LotteryResults> lotteryResultsOptional = resultsCheckerRepository.findById(uuid);
        if (lotteryResultsOptional.isEmpty()) {
            return CheckerDto.ofNotFoundDtoForUuid(uuid);
        }
        return LotteryResultsMapper.toDto(lotteryResultsOptional.get(), CheckerStatus.OK);
    }

    public CheckerDto deleteLotteryResultsForUuid(UUID uuid) {
        Optional<LotteryResults> lotteryResultsOptional = resultsCheckerRepository.findById(uuid);
        if (lotteryResultsOptional.isEmpty()) {
            return CheckerDto.ofNotFoundDtoForUuid(uuid);
        }
        resultsCheckerRepository.deleteById(uuid);
        return LotteryResultsMapper.toDto(lotteryResultsOptional.get(), CheckerStatus.DELETED);
    }

    public List<CheckerDto> getLotteryResultsForDrawDate(LocalDateTime drawDate) {
        List<LotteryResults> lotteryResults = resultsCheckerRepository.findByDrawDate(drawDate);
        return LotteryResultsMapper.toDtoList(lotteryResults, CheckerStatus.OK);
    }

    public List<CheckerDto> getLotteryResultsDrawDateWinnersOnly(LocalDateTime drawDate) {
        List<LotteryResults> lotteryResults = resultsCheckerRepository.findByDrawDateAndIsWinner(drawDate, true);
        return LotteryResultsMapper.toDtoList(lotteryResults, CheckerStatus.OK);
    }

    public List<CheckerDto> getAllLotteryResults() {
        List<LotteryResults> lotteryResults = resultsCheckerRepository.findAll();
        return LotteryResultsMapper.toDtoList(lotteryResults, CheckerStatus.OK);
    }

    public boolean containsResultsForDrawDate(LocalDateTime drawDate) {
        return resultsCheckerRepository.existsByDrawDate(drawDate);
    }

    private List<Integer> retrieveWinningNumbers(LocalDateTime drawDate) {
        WinningNumbersResponseDto winningNumbersResponseDto = winningNumbersService.retrieveWinningNumbers(drawDate);
        if (winningNumbersResponseDto.status() == WinningNumberStatus.NOT_FOUND) {
            winningNumbersService.generateWinningNumbers(drawDate);
            winningNumbersResponseDto = winningNumbersService.retrieveWinningNumbers(drawDate);
        }

        List<Integer> winningNumbers = winningNumbersResponseDto.winningNumbers();
        if (Objects.isNull(winningNumbers)) {
            throw new WinningNumbersServiceFailureException("Results has not been processed. Numbers could not be retrieved.");
        }

        return winningNumbers;
    }

}
