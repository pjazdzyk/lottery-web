package pl.lotto.numberreceiver;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

record Coupon(UUID uuid, LocalDateTime creationDate, LocalDateTime resultsDrawDate,
              LocalDateTime expirationDate, List<Integer> typedNumbers) {

    @Override
    public List<Integer> typedNumbers() {
        return new ArrayList<>(typedNumbers);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coupon userCoupon)) return false;
        return uuid.equals(userCoupon.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

    @Override
    public String toString() {
        return "Coupon{" +
                "uuid=" + uuid +
                ", creationDate=" + creationDate +
                ", resultsDrawDate=" + resultsDrawDate +
                ", expirationDate=" + expirationDate +
                ", typedNumbers=" + typedNumbers +
                '}';
    }

}
