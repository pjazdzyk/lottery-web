package pl.lottery.resultschecker;

import pl.lottery.resultschecker.dto.CheckerDto;
import pl.lottery.resultschecker.dto.CheckerStatus;

import java.util.List;

class LotteryResultsMapper {

    public static CheckerDto toDto(LotteryResults lotteryResults, CheckerStatus status) {
        return new CheckerDto(lotteryResults.uuid(), lotteryResults.drawDate(), lotteryResults.inputNumbers(),
                lotteryResults.winningNumbers(), lotteryResults.matchedNumbers(), lotteryResults.isWinner(), status);
    }

    static List<CheckerDto> toDtoList(List<LotteryResults> lotteryResult, CheckerStatus status) {
        return lotteryResult.stream()
                .map(result -> toDto(result, status))
                .toList();
    }

}
