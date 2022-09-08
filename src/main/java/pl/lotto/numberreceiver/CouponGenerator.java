package pl.lotto.numberreceiver;

import pl.lotto.timegenerator.TimeGeneratorFacade;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

class CouponGenerator {

    private final UUIDGenerator uuidGenerator;
    private final TimeGeneratorFacade timeGeneratorFacade;

    CouponGenerator(UUIDGenerator uuidGenerator, TimeGeneratorFacade timeGeneratorFacade) {
        this.uuidGenerator = uuidGenerator;
        this.timeGeneratorFacade = timeGeneratorFacade;
    }

    Coupon generateUserCoupon(List<Integer> userTypedNumbers) {
        UUID uuid = uuidGenerator.generateRandomUUID();
        LocalDateTime creationTime = timeGeneratorFacade.getCurrentDateAndTime();
        LocalDateTime drawTime = timeGeneratorFacade.getDrawDateAndTime();
        LocalDateTime expirationDate = timeGeneratorFacade.getExpirationDateAndTime();
        return new Coupon(uuid, creationTime, drawTime, expirationDate, userTypedNumbers);
    }

}
