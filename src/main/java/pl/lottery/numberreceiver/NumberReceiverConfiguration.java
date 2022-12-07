package pl.lottery.numberreceiver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.lottery.timegenerator.TimeGeneratorFacade;

@Configuration
public class NumberReceiverConfiguration {

    public NumberReceiverFacade createForTests(ReceiverUuidGenerator receiverUuidGenerator,
                                               CouponRepository repositoryCouponRepository,
                                               TimeGeneratorFacade timeGeneratorFacade,
                                               InputConfigurable inputPropertyConfig) {

        return createForProduction(receiverUuidGenerator, repositoryCouponRepository, timeGeneratorFacade, inputPropertyConfig);
    }

    @Bean("numberReceiverFacade")
    public NumberReceiverFacade createForProduction(ReceiverUuidGenerator receiverUuidGenerator,
                                                    CouponRepository repositoryCouponRepository,
                                                    TimeGeneratorFacade timeGeneratorFacade,
                                                    InputConfigurable inputPropertyConfig) {

        InputValidator inputValidator = new InputValidator(inputPropertyConfig);
        CouponGenerator couponGenerator = new CouponGenerator(receiverUuidGenerator, timeGeneratorFacade);
        return new NumberReceiverFacadeImpl(repositoryCouponRepository, couponGenerator, inputValidator);
    }


}