package pl.lotto.numberreceiver;

import pl.lotto.timegenerator.TimeGeneratorFacade;

public class NumberReceiverConfiguration {

    public NumberReceiverFacade createForTests(UUIDGenerator uuidGenerator, Repository repositoryRepository, TimeGeneratorFacade timeGeneratorFacade) {
        CouponGenerator couponGenerator = new CouponGenerator(uuidGenerator, timeGeneratorFacade);
        return new NumberReceiverFacade(repositoryRepository, couponGenerator, timeGeneratorFacade);
    }

}