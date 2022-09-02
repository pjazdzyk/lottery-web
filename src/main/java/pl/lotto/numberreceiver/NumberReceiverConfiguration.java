package pl.lotto.numberreceiver;

import pl.lotto.temporalgenerator.TemporalGeneratorFacade;

public class NumberReceiverConfiguration {

    public NumberReceiverFacade createForTests(UUIDGenerator uuidGenerator, UserCouponRepository repository, TemporalGeneratorFacade temporalGeneratorFacade) {
        UserCouponGenerator userCouponGenerator = new UserCouponGenerator(uuidGenerator, temporalGeneratorFacade);
        return new NumberReceiverFacade(repository, userCouponGenerator);
    }

}