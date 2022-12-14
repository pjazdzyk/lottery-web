package com.synerset.lottery.numberreceiver;

import com.synerset.lottery.numberreceiver.dto.ReceiverResponseDto;
import com.synerset.lottery.numberreceiver.dto.ReceiverStatus;

import java.util.List;
import java.util.stream.Collectors;

class CouponMapper {

    static ReceiverResponseDto toDto(Coupon coupon, ReceiverStatus status) {
        return new ReceiverResponseDto(coupon.uuid(), coupon.creationDate(), coupon.drawDate(),
                coupon.expirationDate(), List.copyOf(coupon.typedNumbers()), status);
    }

    static List<ReceiverResponseDto> toDtoList(List<Coupon> coupon, ReceiverStatus status) {
        return coupon.stream()
                .map(tempCoupon -> toDto(tempCoupon, status))
                .collect(Collectors.toList());
    }


}