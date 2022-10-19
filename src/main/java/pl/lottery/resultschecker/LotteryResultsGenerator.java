package pl.lottery.resultschecker;

import pl.lottery.numberreceiver.dto.ReceiverResponseDto;

import java.util.List;

class LotteryResultsGenerator {

    private final ResultsChecker resultsChecker;

    public LotteryResultsGenerator(ResultsChecker resultsChecker) {
        this.resultsChecker = resultsChecker;
    }

    List<LotteryResults> generateLotteryResultsList(List<ReceiverResponseDto> couponsForThisDrawDate, List<Integer> winningNumbersForThisDrawDate) {
        return couponsForThisDrawDate.stream().map(coupon -> createSingleLotteryResult(coupon, winningNumbersForThisDrawDate)).toList();
    }

    private LotteryResults createSingleLotteryResult(ReceiverResponseDto receiverResponseDto, List<Integer> winningNumbers) {
        List<Integer> userTypedNumbers = receiverResponseDto.typedNumbers();
        List<Integer> matchedNumbers = resultsChecker.getMatchedNumbers(userTypedNumbers, winningNumbers);
        boolean isWinner = resultsChecker.checkIfIsWinner(matchedNumbers);
        return new LotteryResults(receiverResponseDto.uuid(), receiverResponseDto.drawDateTime(), receiverResponseDto.typedNumbers(), winningNumbers, matchedNumbers, isWinner);
    }

}
