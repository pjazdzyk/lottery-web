package pl.lotto.numberreceiver;

import pl.lotto.numberreceiver.dto.CouponDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

interface Repository {

    CouponDTO save(CouponDTO couponDTO);

    List<CouponDTO> getUserCouponListForDrawDate(LocalDateTime drawDate);

    Optional<CouponDTO> getUserCouponByUUID(UUID uuid);

    Optional<CouponDTO> deleteCouponByUUID(UUID uuid);

    List<CouponDTO> deleteAllExpiredCoupons(LocalDateTime currentDateTime);

}
