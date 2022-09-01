package pl.lotto.numberreceiver;

import pl.lotto.temporalgenerator.TemporalGeneratorFacade;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

class UserCouponGenerator {

    private final UUIDGenerator uuidGenerator;
    private final TemporalGeneratorFacade temporalGeneratorFacade;

    UserCouponGenerator(UUIDGenerator uuidGenerator, TemporalGeneratorFacade temporalGeneratorFacade) {
        this.uuidGenerator = uuidGenerator;
        this.temporalGeneratorFacade = temporalGeneratorFacade;
    }

    UserCoupon generateUserCoupon(List<Integer> userTypedNumbers) {
        UUID uuid = uuidGenerator.generateRandomUUID();
        LocalDateTime creationTime = temporalGeneratorFacade.getCurrentDateAndTime();
        LocalDateTime drawTime = temporalGeneratorFacade.getDrawDateAndTime();
        LocalDateTime expirationDate = temporalGeneratorFacade.getExpirationDateAndTime();
        return new UserCoupon(uuid, creationTime, drawTime, expirationDate, userTypedNumbers);
    }

}
