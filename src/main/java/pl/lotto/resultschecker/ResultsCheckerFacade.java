package pl.lotto.resultschecker;

import pl.lotto.numberreceiver.NumberReceiverFacade;
import pl.lotto.numberreceiver.dto.ReceiverDto;
import pl.lotto.resultschecker.dto.CheckerDto;
import pl.lotto.resultschecker.dto.CheckerStatus;
import pl.lotto.winningnumbergenerator.WinningNumberGeneratorFacade;
import pl.lotto.winningnumbergenerator.dto.WinNumberStatus;
import pl.lotto.winningnumbergenerator.dto.WinningNumbersDto;

import java.time.LocalDateTime;
import java.util.*;

public class ResultsCheckerFacade {
    private final NumberReceiverFacade numberReceiverFacade;
    private final WinningNumberGeneratorFacade winningNumbersFacade;
    private final LotteryResultsGenerator lotteryResultsGenerator;
    private final ResultCheckerRepository resultCheckerRepository;

    public ResultsCheckerFacade(NumberReceiverFacade numberReceiverFacade, WinningNumberGeneratorFacade winningNumbersFacade,
                                LotteryResultsGenerator lotteryResultsGenerator, ResultCheckerRepository resultCheckerRepository) {
        this.numberReceiverFacade = numberReceiverFacade;
        this.winningNumbersFacade = winningNumbersFacade;
        this.lotteryResultsGenerator = lotteryResultsGenerator;
        this.resultCheckerRepository = resultCheckerRepository;
    }

    //TODO: implement scheduled call
    public int generateLotteryResultsForDrawDate(LocalDateTime drawDate) {
        WinningNumbersDto winningNumbersDto = winningNumbersFacade.getWinningNumbersForDate(drawDate);
        int generatedResultsCount = 0;
        if (winningNumbersDto.status() != WinNumberStatus.OK) {
            return generatedResultsCount;
        }
        List<ReceiverDto> receiverDtoForDrawDate = numberReceiverFacade.getUserCouponListForDrawDate(drawDate);
        List<LotteryResults> listOfLotteryResults = lotteryResultsGenerator.getListOfLotteryResults(receiverDtoForDrawDate,
                winningNumbersDto.winningNumbers());
        resultCheckerRepository.saveList(listOfLotteryResults);
        generatedResultsCount = listOfLotteryResults.size();
        return generatedResultsCount;
    }

    public CheckerDto getResultsForId(UUID uuid) {
        Optional<LotteryResults> lotteryResultsOptional = resultCheckerRepository.getLotteryResultsForUuid(uuid);
        if (lotteryResultsOptional.isEmpty()) {
            return notFoundDto();
        }
        return LotteryResultsMapper.toDto(lotteryResultsOptional.get(), CheckerStatus.OK);
    }

    public CheckerDto deleteLotteryResultsForUuid(UUID uuid) {
        Optional<LotteryResults> lotteryResultsOptional = resultCheckerRepository.deleteLotteryResultsForUuid(uuid);
        if (lotteryResultsOptional.isEmpty()) {
            return notFoundDto();
        }
        return LotteryResultsMapper.toDto(lotteryResultsOptional.get(), CheckerStatus.DELETED);
    }

    public List<CheckerDto> getLotteryResultsForDrawDate(LocalDateTime drawDate) {
        List<LotteryResults> lotteryResults = resultCheckerRepository.getLotteryResultsForDrawDate(drawDate);
        return LotteryResultsMapper.toDtoList(lotteryResults, CheckerStatus.OK);
    }

    public List<CheckerDto> getLotteryResultsDrawDateWinnersOnly(LocalDateTime drawDate) {
        List<LotteryResults> lotteryResults = resultCheckerRepository.getLotteryResultsDrawDateWinnersOnly(drawDate);
        return LotteryResultsMapper.toDtoList(lotteryResults, CheckerStatus.OK);
    }

    public List<CheckerDto> deleteLotteryResultsForDrawDate(LocalDateTime drawDate) {
        List<LotteryResults> lotteryResults = resultCheckerRepository.deleteLotteryResultsForDrawDate(drawDate);
        return LotteryResultsMapper.toDtoList(lotteryResults, CheckerStatus.DELETED);
    }

    public List<CheckerDto> getAllLotteryResults() {
        List<LotteryResults> lotteryResults = resultCheckerRepository.getAllLotteryResults();
        return LotteryResultsMapper.toDtoList(lotteryResults, CheckerStatus.OK);
    }

    private CheckerDto notFoundDto() {
        return new CheckerDto(null, null, null, null, null, false, CheckerStatus.NOT_FOUND);
    }



}
