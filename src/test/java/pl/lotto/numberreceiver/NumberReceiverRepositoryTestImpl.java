package pl.lotto.numberreceiver;

import pl.lotto.numberreceiver.dto.UserCouponDTO;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class NumberReceiverRepositoryTestImpl implements UserCouponRepository {

    private final Map<UUID, UserCouponDTO> databaseInMemory = new ConcurrentHashMap<>();

    @Override
    public UserCouponDTO save(UserCouponDTO userCoupon) {
        return databaseInMemory.put(userCoupon.uuid(), userCoupon);
    }

}
