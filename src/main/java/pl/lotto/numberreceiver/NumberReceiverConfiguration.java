package pl.lotto.numberreceiver;

import pl.lotto.timegenerator.TimeGeneratorFacade;

public class NumberReceiverConfiguration {

    public NumberReceiverFacade createForTests(UUIDGenerator uuidGenerator, CouponRepository repositoryCouponRepository, TimeGeneratorFacade timeGeneratorFacade) {
        CouponGenerator couponGenerator = new CouponGenerator(uuidGenerator, timeGeneratorFacade);
        return new NumberReceiverFacade(repositoryCouponRepository, couponGenerator, timeGeneratorFacade);
    }

}