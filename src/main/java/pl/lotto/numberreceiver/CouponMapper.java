package pl.lotto.numberreceiver;

import pl.lotto.numberreceiver.dto.CouponDto;

import java.util.List;
import java.util.stream.Collectors;

class CouponMapper {

    static CouponDto toCouponDto(Coupon coupon) {
        return new CouponDto(coupon.uuid(), coupon.creationDate(), coupon.resultsDrawDate(),
                coupon.expirationDate(), coupon.typedNumbers());
    }

    static Coupon toCoupon(CouponDto couponDto) {
        return new Coupon(couponDto.uuid(), couponDto.creationDate(), couponDto.resultsDrawDate(),
                couponDto.expirationDate(), couponDto.typedNumbers());
    }

    static List<CouponDto> toCouponDtoList(List<Coupon> coupon) {
        return coupon.stream()
                .map(CouponMapper::toCouponDto)
                .collect(Collectors.toList());
    }

    static List<Coupon> toCouponList(List<CouponDto> couponDto) {
        return couponDto.stream()
                .map(CouponMapper::toCoupon)
                .collect(Collectors.toList());
    }


}