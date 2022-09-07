package pl.lotto.numberreceiver;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import pl.lotto.numberreceiver.dto.CouponDTO;
import pl.lotto.timegenerator.TimeGeneratorFacade;

public class NumberReceiverFacade {

    private final Repository userCouponRepository;
    private final CouponGenerator couponGenerator;
    private final TimeGeneratorFacade timeGeneratorFacade;

    public NumberReceiverFacade(Repository numberReceiverRepositoryRepository, CouponGenerator couponGenerator, TimeGeneratorFacade timeGeneratorFacade) {
        this.userCouponRepository = numberReceiverRepositoryRepository;
        this.couponGenerator = couponGenerator;
        this.timeGeneratorFacade = timeGeneratorFacade;
    }

    public CouponDTO inputNumbers(List<Integer> numbersFromUser) {
        CouponDTO couponDTO = couponGenerator.generateUserCoupon(numbersFromUser);
        return userCouponRepository.save(couponDTO);
    }

    public List<CouponDTO> getUserCouponListForDrawDate(LocalDateTime drawDate) {
        return userCouponRepository.getUserCouponListForDrawDate(drawDate);
    }

    Optional<CouponDTO> getUserCouponByUUID(UUID uuid) {
        return userCouponRepository.getUserCouponByUUID(uuid);
    }

    Optional<CouponDTO> deleteUserCouponByUUID(UUID uuid) {
        return userCouponRepository.deleteCouponByUUID(uuid);
    }

    List<CouponDTO> deleteAllExpiredCoupons() {
        //TODO to implement schedules for automatic db cleaning every next draw date
        LocalDateTime currentTime = timeGeneratorFacade.getCurrentDateAndTime();
        return userCouponRepository.deleteAllExpiredCoupons(currentTime);
    }

}


