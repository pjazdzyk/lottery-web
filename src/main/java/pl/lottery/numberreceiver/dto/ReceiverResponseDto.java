package pl.lottery.numberreceiver.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ReceiverResponseDto(UUID uuid,
                                  LocalDateTime creationDateTime,
                                  LocalDateTime drawDateTime,
                                  LocalDateTime expirationDateTime,
                                  List<Integer> typedNumbers,
                                  InputStatus status) {
}
