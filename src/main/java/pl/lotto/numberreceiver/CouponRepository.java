package pl.lotto.numberreceiver;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

interface CouponRepository {

    Coupon save(Coupon coupon);

    List<Coupon> getUserCouponListForDrawDate(LocalDateTime drawDate);

    Optional<Coupon> getUserCouponByUUID(UUID uuid);

    Optional<Coupon> deleteCouponByUUID(UUID uuid);

    List<Coupon> deleteAllExpiredCoupons(LocalDateTime currentDateTime);

}
