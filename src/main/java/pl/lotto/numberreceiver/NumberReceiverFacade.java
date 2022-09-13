package pl.lotto.numberreceiver;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import pl.lotto.numberreceiver.dto.CouponDto;
import pl.lotto.timegenerator.TimeGeneratorFacade;

public class NumberReceiverFacade {

    private final CouponRepository userCouponCouponRepository;
    private final CouponGenerator couponGenerator;
    private final TimeGeneratorFacade timeGeneratorFacade;

    public NumberReceiverFacade(CouponRepository numberReceiverRepositoryCouponRepository, CouponGenerator couponGenerator, TimeGeneratorFacade timeGeneratorFacade) {
        this.userCouponCouponRepository = numberReceiverRepositoryCouponRepository;
        this.couponGenerator = couponGenerator;
        this.timeGeneratorFacade = timeGeneratorFacade;
    }

    public CouponDto inputNumbers(List<Integer> numbersFromUser) {
        Coupon coupon = couponGenerator.generateUserCoupon(numbersFromUser);
        userCouponCouponRepository.save(coupon);
        return CouponMapper.toCouponDto(coupon);
    }

    public Optional<CouponDto> getUserCouponByUUID(UUID uuid) {
        Optional<Coupon> coupon = userCouponCouponRepository.getUserCouponByUUID(uuid);
        return coupon.map(CouponMapper::toCouponDto);
    }

    public List<CouponDto> getUserCouponListForDrawDate(LocalDateTime drawDate) {
        List<Coupon> coupons = userCouponCouponRepository.getUserCouponListForDrawDate(drawDate);
        return CouponMapper.toCouponDtoList(coupons);
    }

    public Optional<CouponDto> deleteUserCouponByUUID(UUID uuid) {
        Optional<Coupon> coupon = userCouponCouponRepository.deleteCouponByUUID(uuid);
        return coupon.map(CouponMapper::toCouponDto);
    }

    public List<CouponDto> deleteAllExpiredCoupons() {
        LocalDateTime currentTime = timeGeneratorFacade.getCurrentDateAndTime();
        List<Coupon> coupons =  userCouponCouponRepository.deleteAllExpiredCoupons(currentTime);
        return CouponMapper.toCouponDtoList(coupons);
    }

    public List<CouponDto> getAllCoupons(){
        List<Coupon> coupons = userCouponCouponRepository.getAllCoupons();
        return CouponMapper.toCouponDtoList(coupons);
    }

}


