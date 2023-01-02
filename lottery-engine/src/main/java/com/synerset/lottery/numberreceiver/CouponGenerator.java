package com.synerset.lottery.numberreceiver;

import com.synerset.lottery.timegenerator.TimeGeneratorFacade;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

class CouponGenerator {

    private final ReceiverUuidGenerator receiverUuidGenerator;
    private final TimeGeneratorFacade timeGeneratorFacade;

    CouponGenerator(ReceiverUuidGenerator receiverUuidGenerator, TimeGeneratorFacade timeGeneratorFacade) {
        this.receiverUuidGenerator = receiverUuidGenerator;
        this.timeGeneratorFacade = timeGeneratorFacade;
    }

    Coupon generateUserCoupon(List<Integer> userTypedNumbers) {
        UUID uuid = receiverUuidGenerator.generateRandomUUID();
        LocalDateTime creationTime = timeGeneratorFacade.retrieveCurrentDateAndTime();
        LocalDateTime drawTime = timeGeneratorFacade.retrieveNextDrawDateAndTime();
        LocalDateTime expirationDate = timeGeneratorFacade.retrieveExpirationDateAndTime();
        return new Coupon(uuid, creationTime, drawTime, expirationDate, userTypedNumbers);
    }

}
