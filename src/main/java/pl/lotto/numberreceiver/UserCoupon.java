package pl.lotto.numberreceiver;

import java.time.LocalDateTime;
import java.util.*;

class UserCoupon {
    private final UUID uuid;
    private final LocalDateTime tokenCreationDate;
    private final LocalDateTime resultsDrawDate;
    private final LocalDateTime tokenExpirationDate;
    private final List<Integer> typedNumbers;

    UserCoupon(UUID uuid, LocalDateTime tokenCreationDate, LocalDateTime resultsDrawDate, LocalDateTime tokenExpirationDate, List<Integer> typedNumbers) {
        this.uuid = uuid;
        this.tokenCreationDate = tokenCreationDate;
        this.resultsDrawDate = resultsDrawDate;
        this.tokenExpirationDate = tokenExpirationDate;
        this.typedNumbers = typedNumbers;
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
