package pl.lotto.numberreceiver;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class NumberReceiverRepositoryTestImpl implements NumberReceiverRepository {

    private final Map<UUID, UserCoupon> databaseInMemory = new ConcurrentHashMap<>();

    @Override
    public UserCoupon save(UserCoupon userCoupon) {
        return databaseInMemory.put(userCoupon.getUuid(), userCoupon);
    }
}
