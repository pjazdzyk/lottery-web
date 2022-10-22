package pl.lottery.numberreceiver;

import pl.lottery.timegenerator.TimeGeneratorFacade;

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
        LocalDateTime creationTime = timeGeneratorFacade.getCurrentDateAndTime();
        LocalDateTime drawTime = timeGeneratorFacade.getNextDrawDateAndTime();
        LocalDateTime expirationDate = timeGeneratorFacade.getExpirationDateAndTime();
        return new Coupon(uuid, creationTime, drawTime, expirationDate, userTypedNumbers);
    }

}
