package pl.lottery.resultschecker;

import pl.lottery.resultschecker.dto.CheckerDto;
import pl.lottery.resultschecker.dto.CheckerStatus;

import java.util.List;

class LotteryResultsMapper {

    public static CheckerDto toDto(LotteryResults lotteryResults, CheckerStatus status) {
        return CheckerDto.CheckerDtoBuilder
                .newInstance(status)
                .withUid(lotteryResults.uuid())
                .withDrawDate(lotteryResults.drawDate())
                .withTypedNumbers(lotteryResults.inputNumbers())
                .withWinningNumbers(lotteryResults.winningNumbers())
                .withMatchedNumbers(lotteryResults.matchedNumbers())
                .withIsWinner(lotteryResults.isWinner())
                .build();
    }

    static List<CheckerDto> toDtoList(List<LotteryResults> lotteryResult, CheckerStatus status) {
        return lotteryResult.stream()
                .map(result -> toDto(result, status))
                .toList();
    }

}
