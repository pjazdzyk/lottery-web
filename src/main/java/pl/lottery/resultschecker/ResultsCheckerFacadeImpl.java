package pl.lottery.resultschecker;

import pl.lottery.infrastructure.winningnumberservice.dto.WinningNumberStatus;
import pl.lottery.infrastructure.winningnumberservice.dto.WinningNumbersResponseDto;
import pl.lottery.numberreceiver.NumberReceiverFacade;
import pl.lottery.numberreceiver.dto.ReceiverResponseDto;
import pl.lottery.resultschecker.dto.CheckerDto;
import pl.lottery.resultschecker.dto.CheckerStatus;
import pl.lottery.resultschecker.exceptions.ResultsGenerationException;

import java.time.LocalDateTime;
import java.util.*;

class ResultsCheckerFacadeImpl implements ResultsCheckerFacade {
    private final NumberReceiverFacade numberReceiverFacade;
    private final WinningNumberGeneratorPort winningNumbersService;
    private final LotteryResultsGenerator lotteryResultsGenerator;
    private final ResultsCheckerRepository resultsCheckerRepository;

    public ResultsCheckerFacadeImpl(NumberReceiverFacade numberReceiverFacade, WinningNumberGeneratorPort winningNumbersService,
                                    LotteryResultsGenerator lotteryResultsGenerator, ResultsCheckerRepository resultsCheckerRepository) {
        this.numberReceiverFacade = numberReceiverFacade;
        this.winningNumbersService = winningNumbersService;
        this.lotteryResultsGenerator = lotteryResultsGenerator;
        this.resultsCheckerRepository = resultsCheckerRepository;
    }

    @Override
    public int generateLotteryResultsForDrawDate(LocalDateTime drawDate) {
        if (containsResultsForDrawDate(drawDate)) {
            return 0;
        }
        List<ReceiverResponseDto> couponsForThisDrawDate = numberReceiverFacade.findUserCouponListForDrawDate(drawDate);
        if(couponsForThisDrawDate.isEmpty()){
            return 0;
        }
        List<Integer> winningNumbersForThisDrawDate = retrieveWinningNumbers(drawDate);
        List<LotteryResults> processedLotteryResults = lotteryResultsGenerator.generateLotteryResultsList(couponsForThisDrawDate, winningNumbersForThisDrawDate);
        resultsCheckerRepository.saveAll(processedLotteryResults);
        return processedLotteryResults.size();
    }

    @Override
    public CheckerDto findResultsForId(UUID uuid) {
        Optional<LotteryResults> lotteryResultsOptional = resultsCheckerRepository.findById(uuid);
        if (lotteryResultsOptional.isEmpty()) {
            return CheckerDto.ofNotFoundDtoForUuid(uuid);
        }
        return LotteryResultsMapper.toDto(lotteryResultsOptional.get(), CheckerStatus.PROCESSED);
    }

    @Override
    public CheckerDto deleteLotteryResultsForUuid(UUID uuid) {
        Optional<LotteryResults> lotteryResultsOptional = resultsCheckerRepository.findById(uuid);
        if (lotteryResultsOptional.isEmpty()) {
            return CheckerDto.ofNotFoundDtoForUuid(uuid);
        }
        resultsCheckerRepository.deleteById(uuid);
        return LotteryResultsMapper.toDto(lotteryResultsOptional.get(), CheckerStatus.DELETED);
    }

    @Override
    public List<CheckerDto> findLotteryResultsForDrawDate(LocalDateTime drawDate) {
        List<LotteryResults> lotteryResults = resultsCheckerRepository.findByDrawDate(drawDate);
        return LotteryResultsMapper.toDtoList(lotteryResults, CheckerStatus.PROCESSED);
    }

    @Override
    public List<CheckerDto> findLotteryResultsDrawDateWinnersOnly(LocalDateTime drawDate) {
        List<LotteryResults> lotteryResults = resultsCheckerRepository.findByDrawDateAndIsWinner(drawDate, true);
        return LotteryResultsMapper.toDtoList(lotteryResults, CheckerStatus.PROCESSED);
    }

    @Override
    public List<CheckerDto> findAllLotteryResults() {
        List<LotteryResults> lotteryResults = resultsCheckerRepository.findAll();
        return LotteryResultsMapper.toDtoList(lotteryResults, CheckerStatus.PROCESSED);
    }

    @Override
    public boolean containsResultsForDrawDate(LocalDateTime drawDate) {
        return resultsCheckerRepository.existsByDrawDate(drawDate);
    }

    private List<Integer> retrieveWinningNumbers(LocalDateTime drawDate) {
        WinningNumbersResponseDto winningNumbersResponseDto = winningNumbersService.retrieveWinningNumbers(drawDate);
        if (winningNumbersResponseDto.status() == WinningNumberStatus.NOT_FOUND) {
            winningNumbersResponseDto = winningNumbersService.generateWinningNumbers(drawDate);
        }
        List<Integer> winningNumbers = winningNumbersResponseDto.winningNumbers();
        if (Objects.isNull(winningNumbers)) {
            throw new ResultsGenerationException("Results has not been processed. Numbers could not be retrieved.");
        }

        return winningNumbers;
    }

}
