package com.synerset.lottery.resultschecker;

import com.synerset.lottery.resultschecker.dto.CheckerDto;
import com.synerset.lottery.resultschecker.dto.CheckerStatus;

import java.util.List;

class LotteryResultsMapper {

    public static CheckerDto toDto(LotteryResults lotteryResults, CheckerStatus status) {
        return CheckerDto.CheckerDtoBuilder
                .newInstance(status)
                .withUid(lotteryResults.uuid())
                .withDrawDate(lotteryResults.drawDate())
                .withTypedNumbers(List.copyOf(lotteryResults.inputNumbers()))
                .withWinningNumbers(List.copyOf(lotteryResults.winningNumbers()))
                .withMatchedNumbers(List.copyOf(lotteryResults.matchedNumbers()))
                .withIsWinner(lotteryResults.isWinner())
                .build();
    }

    static List<CheckerDto> toDtoList(List<LotteryResults> lotteryResult, CheckerStatus status) {
        return lotteryResult.stream()
                .map(result -> toDto(result, status))
                .toList();
    }

}
