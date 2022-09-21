package pl.lotto.numberreceiver;

import pl.lotto.timegenerator.TimeGeneratorFacade;

public class NumberReceiverConfiguration {

    public NumberReceiverFacade createForTests(UUIDGenerator uuidGenerator, CouponRepository repositoryCouponRepository, TimeGeneratorFacade timeGeneratorFacade) {
        LottoInputRules lottoInputRules = new LottoInputRules(1, 99, 6);
        InputValidator inputValidator = new InputValidator(lottoInputRules);
        CouponGenerator couponGenerator = new CouponGenerator(uuidGenerator, timeGeneratorFacade);
        return new NumberReceiverFacade(repositoryCouponRepository, couponGenerator, timeGeneratorFacade, inputValidator);
    }

}