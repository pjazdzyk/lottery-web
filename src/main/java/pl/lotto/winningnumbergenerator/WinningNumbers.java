package pl.lotto.winningnumbergenerator;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Document(collection = "winning-numbers")
record WinningNumbers(@Id UUID uuid,
                      LocalDateTime drawDate,
                      List<Integer> winningNumbers) {
}
