package pl.lotto.numberreceiver;

import pl.lotto.timegenerator.TimeGeneratorFacade;

public class NumberReceiverConfiguration {

    public NumberReceiverFacade createForTests(UUIDGenerator uuidGenerator, CouponRepository repositoryCouponRepository, TimeGeneratorFacade timeGeneratorFacade) {
        InputValidatorRules inputValidatorRules = new InputValidatorRules(1, 99, 6);
        InputValidator inputValidator = new InputValidator(inputValidatorRules);
        CouponGenerator couponGenerator = new CouponGenerator(uuidGenerator, timeGeneratorFacade);
        return new NumberReceiverFacade(repositoryCouponRepository, couponGenerator, timeGeneratorFacade, inputValidator);
    }

}