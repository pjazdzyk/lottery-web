package pl.lotto.numberreceiver;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import pl.lotto.numberreceiver.dto.InputStatus;
import pl.lotto.numberreceiver.dto.ReceiverDto;

public class NumberReceiverFacade {

    private final CouponRepository userCouponCouponRepository;
    private final CouponGenerator couponGenerator;
    private final InputValidator inputValidator;

    public NumberReceiverFacade(CouponRepository numberReceiverRepositoryCouponRepository, CouponGenerator couponGenerator,
                                InputValidator inputValidator) {

        this.userCouponCouponRepository = numberReceiverRepositoryCouponRepository;
        this.couponGenerator = couponGenerator;
        this.inputValidator = inputValidator;
    }

    public ReceiverDto inputNumbers(List<Integer> numbersFromUser) {
        InputStatus inputStatus = inputValidator.validateInput(numbersFromUser);
        if (inputStatus == InputStatus.INVALID) {
            return invalidDto(numbersFromUser, inputStatus);
        }
        Coupon coupon = couponGenerator.generateUserCoupon(numbersFromUser);
        userCouponCouponRepository.save(coupon);
        return CouponMapper.toDto(coupon, InputStatus.SAVED);
    }

    public ReceiverDto getUserCouponByUUID(UUID uuid) {
        Optional<Coupon> couponOptional = userCouponCouponRepository.findById(uuid);
        if (couponOptional.isEmpty()) {
            return notFoundDto();
        }
        return CouponMapper.toDto(couponOptional.get(), InputStatus.SAVED);
    }

    public List<ReceiverDto> getUserCouponListForDrawDate(LocalDateTime drawDate) {
        List<Coupon> coupons = userCouponCouponRepository.findByDrawDate(drawDate);
        return CouponMapper.toDtoList(coupons, InputStatus.SAVED);
    }

    public ReceiverDto deleteUserCouponByUUID(UUID uuid) {
        Optional<Coupon> couponOptional = userCouponCouponRepository.findById(uuid);
        if (couponOptional.isEmpty()) {
            return notFoundDto();
        }
        userCouponCouponRepository.deleteById(uuid);
        return CouponMapper.toDto(couponOptional.get(), InputStatus.DELETED);
    }

    public List<ReceiverDto> getAllCoupons() {
        List<Coupon> coupons = userCouponCouponRepository.findAll();
        return CouponMapper.toDtoList(coupons, InputStatus.SAVED);
    }

    private ReceiverDto invalidDto(List<Integer> numbersFromUser, InputStatus status) {
        return new ReceiverDto(null, null, null, null, numbersFromUser, status);
    }

    private ReceiverDto notFoundDto() {
        return new ReceiverDto(null, null, null, null, null, InputStatus.NOT_FOUND);
    }
}


