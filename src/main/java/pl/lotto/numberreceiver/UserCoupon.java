package pl.lotto.numberreceiver;

import java.time.LocalDateTime;
import java.util.*;

class UserCoupon {

    private final UUID uuid;
    private final LocalDateTime tokenCreationDate;
    private final LocalDateTime resultsDrawDate;
    private final int expirationLengthInYears = 2;
    private final LocalDateTime tokenExpirationDate;
    private final List<Integer> typedNumbers;

    UserCoupon(LocalDateTime tokenCreationTime, LocalDateTime resultsDrawDate, List<Integer> typedNumbers) {
        this.tokenCreationDate = tokenCreationTime;
        this.resultsDrawDate = resultsDrawDate;
        this.typedNumbers = typedNumbers;
        this.tokenExpirationDate = createExpirationDate();
        this.uuid = UUID.randomUUID();
    }

    LocalDateTime createExpirationDate() {
        return resultsDrawDate.plusYears(expirationLengthInYears);
    }

    boolean checkIfTokenIsValid() {
        LocalDateTime currentDatetime = LocalDateTime.now();
        return currentDatetime.isAfter(tokenExpirationDate);
    }

    UUID getUuid() {
        return uuid;
    }

    LocalDateTime getTokenCreationDate() {
        return tokenCreationDate;
    }

    LocalDateTime getResultsDrawDate() {
        return resultsDrawDate;
    }

    List<Integer> getTypedNumbers() {
        return new ArrayList<>(typedNumbers);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserCoupon userToken)) return false;
        return uuid.equals(userToken.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

    @Override
    public String toString() {
        return "UserToken{" +
                "uuid=" + uuid +
                ", typedNumbers=" + typedNumbers +
                '}';
    }
}
