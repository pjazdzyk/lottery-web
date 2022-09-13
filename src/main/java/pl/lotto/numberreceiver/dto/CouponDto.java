package pl.lotto.numberreceiver.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public record CouponDto(UUID uuid, LocalDateTime creationDateTime, LocalDateTime drawDateTime,
                        LocalDateTime expirationDateTime, List<Integer> typedNumbers) {

    @Override
    public List<Integer> typedNumbers() {
        return new ArrayList<>(typedNumbers);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CouponDto userToken)) return false;
        return uuid.equals(userToken.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

    @Override
    public String toString() {
        return "CouponDTO{" +
                "uuid=" + uuid +
                ", creationDateTime=" + creationDateTime +
                ", drawDateTime=" + drawDateTime +
                ", expirationDateTime=" + expirationDateTime +
                ", typedNumbers=" + typedNumbers +
                '}';
    }
}
