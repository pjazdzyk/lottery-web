package pl.lotto.numberreceiver;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

interface CouponRepository {

    Coupon save(Coupon coupon);

    Optional<Coupon> getUserCouponByUUID(UUID uuid);

    List<Coupon> getUserCouponListForDrawDate(LocalDateTime drawDate);

    Optional<Coupon> deleteCouponByUUID(UUID uuid);

    List<Coupon> deleteAllExpiredCoupons(LocalDateTime currentDateTime);

    List<Coupon> getAllCoupons();

}
