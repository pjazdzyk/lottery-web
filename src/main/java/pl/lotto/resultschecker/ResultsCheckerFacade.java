package pl.lotto.resultschecker;

import pl.lotto.numberreceiver.NumberReceiverFacade;
import pl.lotto.numberreceiver.dto.ReceiverDto;
import pl.lotto.resultschecker.dto.LotteryResultsDto;
import pl.lotto.winningnumbergenerator.WinningNumberGeneratorFacade;
import pl.lotto.winningnumbergenerator.dto.WinningNumbersDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    //TODO: this may fail and overflow heap for large lists of coupons
    //TODO: this wil consume a lot of memory for large lists of coupon - to develop some way of buffered processing
    public int generateLotteryResultsForDrawDate(LocalDateTime drawDate) {
        Optional<WinningNumbersDto> currentWinningNumbersOptional = winningNumbersFacade.getWinningNumbersForDate(drawDate);
        WinningNumbersDto winningNumbersDto;
        int generatedResultsCount = 0;
        if (currentWinningNumbersOptional.isPresent()) {
            winningNumbersDto = currentWinningNumbersOptional.get();
        } else {
            return generatedResultsCount;
        }
        List<ReceiverDto> receiverDtoForDrawDate = numberReceiverFacade.getUserCouponListForDrawDate(drawDate);
        List<LotteryResults> listOfLotteryResults = lotteryResultsGenerator.getListOfLotteryResults(receiverDtoForDrawDate, winningNumbersDto.winningNumbers());
        resultCheckerRepository.saveList(listOfLotteryResults);
        generatedResultsCount = listOfLotteryResults.size();
        return generatedResultsCount;
    }

    public Optional<LotteryResultsDto> getResultsForId(UUID uuid) {
        Optional<LotteryResults> lotteryResultsOptional = resultCheckerRepository.getLotteryResultsForUuid(uuid);
        return lotteryResultsOptional.map(LotteryResultsMapper::toDto);
    }

    public Optional<LotteryResultsDto> deleteLotteryResultsForUuid(UUID uuid) {
        Optional<LotteryResults> lotteryResultsOptional = resultCheckerRepository.deleteLotteryResultsForUuid(uuid);
        return lotteryResultsOptional.map(LotteryResultsMapper::toDto);
    }

    public List<LotteryResultsDto> getLotteryResultsForDrawDate(LocalDateTime drawDate) {
        List<LotteryResults> lotteryResults = resultCheckerRepository.getLotteryResultsForDrawDate(drawDate);
        return LotteryResultsMapper.toDtoList(lotteryResults);
    }

    public List<LotteryResultsDto> getLotteryResultsDrawDateWinnersOnly(LocalDateTime drawDate){
        List<LotteryResults> lotteryResults = resultCheckerRepository.getLotteryResultsDrawDateWinnersOnly(drawDate);
        return LotteryResultsMapper.toDtoList(lotteryResults);
    }

    public List<LotteryResultsDto> deleteLotteryResultsForDrawDate(LocalDateTime drawDate) {
        List<LotteryResults> lotteryResults = resultCheckerRepository.deleteLotteryResultsForDrawDate(drawDate);
        return LotteryResultsMapper.toDtoList(lotteryResults);
    }

    public List<LotteryResultsDto> getAllLotteryResults() {
        List<LotteryResults> lotteryResults = resultCheckerRepository.getAllLotteryResults();
        return LotteryResultsMapper.toDtoList(lotteryResults);
    }

}
