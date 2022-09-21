package pl.lotto.numberreceiver;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Document
record Coupon(@Id UUID uuid, LocalDateTime creationDate, LocalDateTime resultsDrawDate,
              LocalDateTime expirationDate, List<Integer> typedNumbers) {
}
