package pl.lotto.numberreceiver.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ReceiverDto(UUID uuid, LocalDateTime creationDateTime, LocalDateTime drawDateTime,
                          LocalDateTime expirationDateTime, List<Integer> typedNumbers, InputStatus status) {
}
