package pl.lottery.numberreceiver;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Document(collection = "input-coupons")
record Coupon(@Id UUID uuid,
                     LocalDateTime creationDate,
                     LocalDateTime drawDate,
                     LocalDateTime expirationDate,
                     List<Integer> typedNumbers) {
}
