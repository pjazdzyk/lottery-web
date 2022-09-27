package pl.lotto.resultschecker;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Document(collection = "lottery-results")
record LotteryResults(@Id UUID uuid,
                      LocalDateTime drawDate,
                      List<Integer> inputNumbers,
                      List<Integer> winningNumbers,
                      List<Integer> matchedNumbers, boolean isWinner) {
}