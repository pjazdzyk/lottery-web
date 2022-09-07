package pl.lotto.numberreceiver;

import pl.lotto.numberreceiver.dto.CouponDTO;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

class NumberReceiverRepositoryRepository implements Repository {

    private final Map<UUID, CouponDTO> databaseInMemory = new ConcurrentHashMap<>();

    @Override
    public CouponDTO save(CouponDTO couponDTO) {
        databaseInMemory.put(couponDTO.uuid(), couponDTO);
        return couponDTO;
    }

    @Override
    public List<CouponDTO> getUserCouponListForDrawDate(LocalDateTime drawDate) {
        return databaseInMemory.values()
                .stream()
                .filter(coupon -> drawDate.isEqual(coupon.resultsDrawDate()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CouponDTO> getUserCouponByUUID(UUID uuid) {
        return Optional.ofNullable(databaseInMemory.get(uuid));
    }

    @Override
    public Optional<CouponDTO> deleteCouponByUUID(UUID uuid) {
        return Optional.ofNullable(databaseInMemory.remove(uuid));
    }

    @Override
    public List<CouponDTO> deleteAllExpiredCoupons(LocalDateTime currentDateTime) {
        List<CouponDTO> toBeRemoved = databaseInMemory.values()
                .stream()
                .filter(coupon -> currentDateTime.isAfter(coupon.expirationDate()))
                .toList();
        toBeRemoved.forEach(coupon -> databaseInMemory.remove(coupon.uuid()));
        return toBeRemoved;
    }


}
