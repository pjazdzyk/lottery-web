package pl.lotto.numberreceiver;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class NumberReceiverRepositoryStub implements CouponRepository {

    private final Map<UUID, Coupon> databaseInMemory = new ConcurrentHashMap<>();

    @Override
    public Coupon save(Coupon coupon) {
        databaseInMemory.put(coupon.uuid(), coupon);
        return coupon;
    }

    @Override
    public List<Coupon> getUserCouponListForDrawDate(LocalDateTime drawDate) {
        return databaseInMemory.values()
                .stream()
                .filter(coupon -> drawDate.isEqual(coupon.resultsDrawDate()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Coupon> getUserCouponByUUID(UUID uuid) {
        return Optional.ofNullable(databaseInMemory.get(uuid));
    }

    @Override
    public Optional<Coupon> deleteCouponByUUID(UUID uuid) {
        return Optional.ofNullable(databaseInMemory.remove(uuid));
    }

    @Override
    public List<Coupon> deleteAllExpiredCoupons(LocalDateTime currentDateTime) {
        List<Coupon> toBeRemoved = databaseInMemory.values()
                .stream()
                .filter(coupon -> currentDateTime.isAfter(coupon.expirationDate()))
                .toList();
        toBeRemoved.forEach(coupon -> databaseInMemory.remove(coupon.uuid()));
        return toBeRemoved;
    }

    @Override
    public List<Coupon> getAllCoupons() {
        return new ArrayList<>(databaseInMemory.values());
    }


}
