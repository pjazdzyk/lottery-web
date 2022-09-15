package pl.lotto.numberreceiver;

import pl.lotto.numberreceiver.dto.InputStatus;
import pl.lotto.numberreceiver.dto.ReceiverDto;

import java.util.List;
import java.util.stream.Collectors;

class CouponMapper {

    static ReceiverDto toDto(Coupon coupon, InputStatus status) {
        return new ReceiverDto(coupon.uuid(), coupon.creationDate(), coupon.resultsDrawDate(),
                coupon.expirationDate(), coupon.typedNumbers(), status);
    }

    static List<ReceiverDto> toDtoList(List<Coupon> coupon, InputStatus status) {
        return coupon.stream()
                .map(tempCoupon -> toDto(tempCoupon,status))
                .collect(Collectors.toList());
    }


}