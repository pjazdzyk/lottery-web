package pl.lotto.resultschecker;

import pl.lotto.resultschecker.dto.CheckerDto;
import pl.lotto.resultschecker.dto.CheckerStatus;

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
