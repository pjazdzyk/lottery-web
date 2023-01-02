package com.synerset.lottery.numberreceiver;

import com.synerset.lottery.numberreceiver.dto.ReceiverResponseDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface NumberReceiverFacade {
    ReceiverResponseDto inputNumbers(List<Integer> numbersFromUser);

    ReceiverResponseDto findUserCouponByUUID(UUID uuid);

    List<ReceiverResponseDto> findUserCouponListForDrawDate(LocalDateTime drawDate);

    ReceiverResponseDto deleteUserCouponByUUID(UUID uuid);

    List<ReceiverResponseDto> findAllCoupons();

    boolean numbersAreNotValid(List<Integer> numbersFromUser);
}
