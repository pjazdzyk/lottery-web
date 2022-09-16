package pl.lotto.resultschecker;

import pl.lotto.numberreceiver.dto.ReceiverDto;

import java.util.List;

class LotteryResultsGenerator {

    private final ResultsChecker resultsChecker;

    public LotteryResultsGenerator(ResultsChecker resultsChecker) {
        this.resultsChecker = resultsChecker;
    }

    List<LotteryResults> getListOfLotteryResults(List<ReceiverDto> couponSubListForDrawDate, List<Integer> winningNumbers) {
        return couponSubListForDrawDate.stream().map(coupon -> createLotteryResult(coupon, winningNumbers)).toList();
    }

    private LotteryResults createLotteryResult(ReceiverDto receiverDto, List<Integer> winningNumbers) {
        List<Integer> userTypedNumbers = receiverDto.typedNumbers();
        List<Integer> matchedNumbers = resultsChecker.getMatchedNumbers(userTypedNumbers, winningNumbers);
        boolean isWinner = resultsChecker.checkIfIsWinner(matchedNumbers);
        return new LotteryResults(receiverDto.uuid(), receiverDto.drawDateTime(), receiverDto.typedNumbers(), winningNumbers, matchedNumbers, isWinner);
    }

}
