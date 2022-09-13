package pl.lotto.resultschecker;

import pl.lotto.resultschecker.dto.LotteryResultsDto;

import java.util.List;
import java.util.stream.Collectors;

class LotteryResultsMapper {

    public static LotteryResults toLotteryResults(LotteryResultsDto lotteryResultsDto) {
        return new LotteryResults(lotteryResultsDto.uuid(), lotteryResultsDto.drawDate(), lotteryResultsDto.winningNumbers(),
                lotteryResultsDto.matchedNumbers(), lotteryResultsDto.isWinner());
    }

    public static LotteryResultsDto toDto(LotteryResults lotteryResults) {
        return new LotteryResultsDto(lotteryResults.uuid(), lotteryResults.drawDate(), lotteryResults.winningNumbers(),
                lotteryResults.matchedNumbers(), lotteryResults.isWinner());
    }

    static List<LotteryResultsDto> toDtoList(List<LotteryResults> LotteryResults) {
        return LotteryResults.stream()
                .map(LotteryResultsMapper::toDto)
                .collect(Collectors.toList());
    }

    static List<LotteryResults> toLotteryResultsList(List<LotteryResultsDto> couponDto) {
        return couponDto.stream()
                .map(LotteryResultsMapper::toLotteryResults)
                .collect(Collectors.toList());
    }

}
