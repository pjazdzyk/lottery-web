package pl.lotto.numberreceiver;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import pl.lotto.numberreceiver.dto.InputStatus;
import pl.lotto.numberreceiver.dto.ReceiverDto;
import pl.lotto.timegenerator.TimeGeneratorFacade;

public class NumberReceiverFacade {

    private final CouponRepository userCouponCouponRepository;
    private final CouponGenerator couponGenerator;
    private final TimeGeneratorFacade timeGeneratorFacade;
    private final InputValidator inputValidator;

    public NumberReceiverFacade(CouponRepository numberReceiverRepositoryCouponRepository, CouponGenerator couponGenerator,
                                TimeGeneratorFacade timeGeneratorFacade, InputValidator inputValidator) {
        this.userCouponCouponRepository = numberReceiverRepositoryCouponRepository;
        this.couponGenerator = couponGenerator;
        this.timeGeneratorFacade = timeGeneratorFacade;
        this.inputValidator = inputValidator;
    }

    public ReceiverDto inputNumbers(List<Integer> numbersFromUser) {
        InputStatus inputStatus = inputValidator.isValidInput(numbersFromUser);
        if (inputStatus == InputStatus.INVALID) {
            return invalidDto(numbersFromUser, inputStatus);
        }
        Coupon coupon = couponGenerator.generateUserCoupon(numbersFromUser);
        userCouponCouponRepository.save(coupon);
        return CouponMapper.toDto(coupon, InputStatus.SAVED);
    }

    public Optional<ReceiverDto> getUserCouponByUUID(UUID uuid) {
        Optional<Coupon> coupon = userCouponCouponRepository.getUserCouponByUUID(uuid);
        return coupon.map(couponOpt -> CouponMapper.toDto(couponOpt, InputStatus.SAVED));
    }

    public List<ReceiverDto> getUserCouponListForDrawDate(LocalDateTime drawDate) {
        List<Coupon> coupons = userCouponCouponRepository.getUserCouponListForDrawDate(drawDate);
        return CouponMapper.toDtoList(coupons, InputStatus.SAVED);
    }

    public Optional<ReceiverDto> deleteUserCouponByUUID(UUID uuid) {
        Optional<Coupon> coupon = userCouponCouponRepository.deleteCouponByUUID(uuid);
        return coupon.map(couponOpt -> CouponMapper.toDto(couponOpt, InputStatus.DELETED));
    }

    public List<ReceiverDto> deleteAllExpiredCoupons() {
        LocalDateTime currentTime = timeGeneratorFacade.getCurrentDateAndTime();
        List<Coupon> coupons = userCouponCouponRepository.deleteAllExpiredCoupons(currentTime);
        return CouponMapper.toDtoList(coupons, InputStatus.DELETED);
    }

    public List<ReceiverDto> getAllCoupons() {
        List<Coupon> coupons = userCouponCouponRepository.getAllCoupons();
        return CouponMapper.toDtoList(coupons, InputStatus.SAVED);
    }

    private ReceiverDto invalidDto(List<Integer> numbersFromUser, InputStatus status) {
        return new ReceiverDto(null, null, null, null, numbersFromUser, status);
    }

}


