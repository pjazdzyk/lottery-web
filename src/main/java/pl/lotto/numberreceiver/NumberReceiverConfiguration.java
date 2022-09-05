package pl.lotto.numberreceiver;

import pl.lotto.timegenerator.TimeGeneratorFacade;

public class NumberReceiverConfiguration {

    public NumberReceiverFacade createForTests(UUIDGenerator uuidGenerator, Repository repositoryRepository, TimeGeneratorFacade timeGeneratorFacade) {
        UserCouponGenerator userCouponGenerator = new UserCouponGenerator(uuidGenerator, timeGeneratorFacade);
        return new NumberReceiverFacade(repositoryRepository, userCouponGenerator, timeGeneratorFacade);
    }

}