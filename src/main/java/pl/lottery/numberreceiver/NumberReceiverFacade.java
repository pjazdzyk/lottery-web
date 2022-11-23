package pl.lottery.numberreceiver;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import pl.lottery.numberreceiver.dto.ReceiverStatus;
import pl.lottery.numberreceiver.dto.ReceiverResponseDto;
import pl.lottery.numberreceiver.exceptions.InvalidInputNumbersException;

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
        if (numbersAreNotValid(numbersFromUser)) {
            throw new InvalidInputNumbersException("Invalid input numbers, please check the number constraint rules.");
        }
        Coupon coupon = couponGenerator.generateUserCoupon(numbersFromUser);
        userCouponCouponRepository.save(coupon);
        return CouponMapper.toDto(coupon, ReceiverStatus.SAVED);
    }

    public ReceiverResponseDto findUserCouponByUUID(UUID uuid) {
        Optional<Coupon> couponOptional = userCouponCouponRepository.findById(uuid);
        if (couponOptional.isEmpty()) {
            return notFoundDto();
        }
        return CouponMapper.toDto(couponOptional.get(), ReceiverStatus.SAVED);
    }

    public List<ReceiverResponseDto> findUserCouponListForDrawDate(LocalDateTime drawDate) {
        List<Coupon> coupons = userCouponCouponRepository.findByDrawDate(drawDate);
        return CouponMapper.toDtoList(coupons, ReceiverStatus.SAVED);
    }

    public ReceiverResponseDto deleteUserCouponByUUID(UUID uuid) {
        Optional<Coupon> couponOptional = userCouponCouponRepository.findById(uuid);
        if (couponOptional.isEmpty()) {
            return notFoundDto();
        }
        userCouponCouponRepository.deleteById(uuid);
        return CouponMapper.toDto(couponOptional.get(), ReceiverStatus.DELETED);
    }

    public List<ReceiverResponseDto> findAllCoupons() {
        List<Coupon> coupons = userCouponCouponRepository.findAll();
        return CouponMapper.toDtoList(coupons, ReceiverStatus.SAVED);
    }

    public boolean numbersAreNotValid(List<Integer> numbersFromUser) {
        return !inputValidator.isValidInput(numbersFromUser);
    }

    private ReceiverResponseDto notFoundDto() {
        return new ReceiverResponseDto(null, null, null, null, null, ReceiverStatus.NOT_FOUND);
    }
}


