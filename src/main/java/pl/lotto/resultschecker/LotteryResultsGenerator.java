package pl.lotto.resultschecker;

import pl.lotto.numberreceiver.dto.CouponDto;

import java.util.List;

class LotteryResultsGenerator {

    private final ResultsChecker resultsChecker;

    public LotteryResultsGenerator(ResultsChecker resultsChecker) {
        this.resultsChecker = resultsChecker;
    }

    List<LotteryResults> getListOfLotteryResults(List<CouponDto> couponSubListForDrawDate, List<Integer> winningNumbers) {
        return couponSubListForDrawDate.stream().map(coupon -> createLotteryResult(coupon, winningNumbers)).toList();
    }

    private LotteryResults createLotteryResult(CouponDto couponDto, List<Integer> winningNumbers) {
        List<Integer> userTypedNumbers = couponDto.typedNumbers();
        List<Integer> matchedNumbers = resultsChecker.getMatchedNumbers(userTypedNumbers, winningNumbers);
        boolean isWinner = resultsChecker.checkIfIsWinner(matchedNumbers);
        return new LotteryResults(couponDto.uuid(), couponDto.drawDateTime(), winningNumbers, matchedNumbers, isWinner);
    }

}
