package pl.lotto.resultschecker;

import pl.lotto.numberreceiver.dto.ReceiverResponseDto;

import java.util.List;

class LotteryResultsGenerator {

    private final ResultsChecker resultsChecker;

    public LotteryResultsGenerator(ResultsChecker resultsChecker) {
        this.resultsChecker = resultsChecker;
    }

    List<LotteryResults> getListOfLotteryResults(List<ReceiverResponseDto> couponSubListForDrawDate, List<Integer> winningNumbers) {
        return couponSubListForDrawDate.stream().map(coupon -> createLotteryResult(coupon, winningNumbers)).toList();
    }

    private LotteryResults createLotteryResult(ReceiverResponseDto receiverResponseDto, List<Integer> winningNumbers) {
        List<Integer> userTypedNumbers = receiverResponseDto.typedNumbers();
        List<Integer> matchedNumbers = resultsChecker.getMatchedNumbers(userTypedNumbers, winningNumbers);
        boolean isWinner = resultsChecker.checkIfIsWinner(matchedNumbers);
        return new LotteryResults(receiverResponseDto.uuid(), receiverResponseDto.drawDateTime(), receiverResponseDto.typedNumbers(), winningNumbers, matchedNumbers, isWinner);
    }

}
