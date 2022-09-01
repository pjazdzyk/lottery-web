package pl.lotto.numberreceiver;

import java.util.List;

import pl.lotto.numberreceiver.dto.UserCouponDTO;

public class NumberReceiverFacade {

    private final UserCouponRepository userCouponRepository;
    final private UserCouponGenerator userCouponGenerator;

    public NumberReceiverFacade(UserCouponRepository numberReceiverRepository, UserCouponGenerator userCouponGenerator) {
        this.userCouponRepository = numberReceiverRepository;
        this.userCouponGenerator = userCouponGenerator;
    }

    public UserCouponDTO inputNumbers(List<Integer> numbersFromUser) {
        UserCoupon userCoupon = userCouponGenerator.generateUserCoupon(numbersFromUser);
        UserCouponDTO userCouponDTO = mapUserCouponToDTO(userCoupon);
        userCouponRepository.save(userCouponDTO);
        return userCouponDTO;
    }

    private UserCouponDTO mapUserCouponToDTO(UserCoupon userCoupon) {
        return new UserCouponDTO(userCoupon.getUuid(), userCoupon.getTokenCreationDate(), userCoupon.getResultsDrawDate(),
                userCoupon.getResultsDrawDate(), userCoupon.getTypedNumbers());
    }

}


