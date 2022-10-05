package pl.lotto.numberreceiver;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import pl.lotto.numberreceiver.dto.InputStatus;
import pl.lotto.numberreceiver.dto.ReceiverResponseDto;

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

    public ReceiverResponseDto inputNumbers(List<Integer> numbersFromUser) {
        InputStatus inputStatus = inputValidator.validateInput(numbersFromUser);
        if (inputStatus == InputStatus.INVALID) {
            return invalidDto(numbersFromUser, inputStatus);
        }
        Coupon coupon = couponGenerator.generateUserCoupon(numbersFromUser);
        userCouponCouponRepository.save(coupon);
        return CouponMapper.toDto(coupon, InputStatus.SAVED);
    }

    public ReceiverResponseDto getUserCouponByUUID(UUID uuid) {
        Optional<Coupon> couponOptional = userCouponCouponRepository.findById(uuid);
        if (couponOptional.isEmpty()) {
            return notFoundDto();
        }
        return CouponMapper.toDto(couponOptional.get(), InputStatus.SAVED);
    }

    public List<ReceiverResponseDto> getUserCouponListForDrawDate(LocalDateTime drawDate) {
        List<Coupon> coupons = userCouponCouponRepository.findByDrawDate(drawDate);
        return CouponMapper.toDtoList(coupons, InputStatus.SAVED);
    }

    public ReceiverResponseDto deleteUserCouponByUUID(UUID uuid) {
        Optional<Coupon> couponOptional = userCouponCouponRepository.findById(uuid);
        if (couponOptional.isEmpty()) {
            return notFoundDto();
        }
        userCouponCouponRepository.deleteById(uuid);
        return CouponMapper.toDto(couponOptional.get(), InputStatus.DELETED);
    }

    public List<ReceiverResponseDto> getAllCoupons() {
        List<Coupon> coupons = userCouponCouponRepository.findAll();
        return CouponMapper.toDtoList(coupons, InputStatus.SAVED);
    }

    private ReceiverResponseDto invalidDto(List<Integer> numbersFromUser, InputStatus status) {
        return new ReceiverResponseDto(null, null, null, null, numbersFromUser, status);
    }

    private ReceiverResponseDto notFoundDto() {
        return new ReceiverResponseDto(null, null, null, null, null, InputStatus.NOT_FOUND);
    }
}


