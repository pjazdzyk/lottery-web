package pl.lotto.numberreceiver;

import pl.lotto.numberreceiver.dto.UserCouponDTO;

interface UserCouponRepository {
    UserCouponDTO save(UserCouponDTO userCoupon);

}
