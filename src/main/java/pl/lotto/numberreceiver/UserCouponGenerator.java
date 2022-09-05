package pl.lotto.numberreceiver;

import pl.lotto.numberreceiver.dto.CouponDTO;
import pl.lotto.timegenerator.TimeGeneratorFacade;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

class UserCouponGenerator {

    private final UUIDGenerator uuidGenerator;
    private final TimeGeneratorFacade timeGeneratorFacade;

    UserCouponGenerator(UUIDGenerator uuidGenerator, TimeGeneratorFacade timeGeneratorFacade) {
        this.uuidGenerator = uuidGenerator;
        this.timeGeneratorFacade = timeGeneratorFacade;
    }

    CouponDTO generateUserCoupon(List<Integer> userTypedNumbers) {
        UUID uuid = uuidGenerator.generateRandomUUID();
        LocalDateTime creationTime = timeGeneratorFacade.getCurrentDateAndTime();
        LocalDateTime drawTime = timeGeneratorFacade.getDrawDateAndTime();
        LocalDateTime expirationDate = timeGeneratorFacade.getExpirationDateAndTime();
        return new CouponDTO(uuid, creationTime, drawTime, expirationDate, userTypedNumbers);
    }

}
