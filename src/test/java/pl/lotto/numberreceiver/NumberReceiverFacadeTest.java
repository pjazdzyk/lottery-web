package pl.lotto.numberreceiver;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.lotto.numberreceiver.dto.CouponDto;
import pl.lotto.timegenerator.SampleClock;
import pl.lotto.timegenerator.TimeGeneratorConfiguration;
import pl.lotto.timegenerator.TimeGeneratorFacade;

import java.time.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class NumberReceiverFacadeTest implements SampleClock, MockedUUIDGenerator {

    final private TimeGeneratorConfiguration timeGeneratorConfig = new TimeGeneratorConfiguration();
    final private NumberReceiverConfiguration numberReceiverConfig = new NumberReceiverConfiguration();
    private final UUIDGenerator uuidGenerator = new UUIDGenerator();
    private final Clock defaultClockForTests = sampleClock(LocalDate.of(2022, 8, 10), LocalTime.of(8, 0));
    private final TimeGeneratorFacade timeGeneratorFacade = timeGeneratorConfig.createForTest(defaultClockForTests);
    private CouponRepository receiverCouponRepository = new NumberReceiverRepositoryCouponRepository();
    private final NumberReceiverFacade numberReceiverFacade = numberReceiverConfig.createForTests(uuidGenerator, receiverCouponRepository, timeGeneratorFacade);
    private final UUID uuidForMocks = UUID.fromString("ef241277-64a2-457a-beea-a4c589803a26");

    @AfterEach
    void tearDown() {
        receiverCouponRepository = new NumberReceiverRepositoryCouponRepository();
    }

    @Test
    @DisplayName("should save coupon when user numbers are provided")
    void inputNumbers_givenInputNumbers_returnsResultDTOWithUserNumbers() {
        // given
        List<Integer> numbersFromUser = List.of(1, 2, 3, 4, 5, 6);

        // when
        CouponDto couponToBeAddedToRepDto = numberReceiverFacade.inputNumbers(numbersFromUser);
        Coupon couponToBeAddedToRep = CouponMapper.toCoupon(couponToBeAddedToRepDto);
        UUID actualUUID = couponToBeAddedToRep.uuid();
        Optional<Coupon> actualCouponOptional = receiverCouponRepository.getUserCouponByUUID(actualUUID);
        Coupon actualCoupon = actualCouponOptional.orElse(null);

        // then
        assertThat(actualCoupon).isEqualTo(couponToBeAddedToRep);
    }

    @Test
    @DisplayName("should return list of coupons when draw date is provided")
    void getUserCouponsListForDrawDate_givenDrawDate_returnsUserCouponForExpectedDrawDate() {
        // given
        seedSomeCouponsToTestDB(10);
        // Advancing in time from 08.10 to 08.13
        Clock clock3DaysLater = sampleClock(LocalDate.of(2022, 8, 14), LocalTime.of(8, 0));
        TimeGeneratorFacade timeGeneratorFacade = timeGeneratorConfig.createForTest(clock3DaysLater);
        NumberReceiverFacade numberReceiverFacade = numberReceiverConfig.createForTests(uuidGenerator, receiverCouponRepository, timeGeneratorFacade);
        LocalDateTime laterDrawDate = timeGeneratorFacade.getDrawDateAndTime();
        CouponDto expectedCoupon = numberReceiverFacade.inputNumbers(List.of(13, 14, 15, 16, 17, 18));

        // when
        List<CouponDto> actualCouponLists = numberReceiverFacade.getUserCouponListForDrawDate(laterDrawDate);

        // then
        assertThat(actualCouponLists).contains(expectedCoupon);
        assertThat(actualCouponLists.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("should return coupon when coupon UUID is provided")
    void getUserCouponByUUID_givenUUID_returnsUserCouponForExpectedUUID() {
        // given
        NumberReceiverFacade numberReceiverFacade = getNumberReceiverFacadeWithMockedUUID(uuidForMocks);
        numberReceiverFacade.inputNumbers(List.of(1, 2, 3, 4, 5, 6));

        // when
        Optional<CouponDto> actualUserCoupon = numberReceiverFacade.getUserCouponByUUID(uuidForMocks);
        UUID actualUUID = actualUserCoupon.map(CouponDto::uuid).orElse(null);

        // then
        assertThat(actualUUID).isEqualTo(uuidForMocks);
    }

    @Test
    @DisplayName("should delete coupon when coupon UUID is provided")
    void deleteUserCouponByUUID_givenUUID_returnDeletedCouponForExpectedUUID() {
        // given
        NumberReceiverFacade numberReceiverFacade = getNumberReceiverFacadeWithMockedUUID(uuidForMocks);
        numberReceiverFacade.inputNumbers(List.of(1, 2, 3, 4, 5, 6));

        // when
        Optional<CouponDto> actualUserCoupon = numberReceiverFacade.deleteUserCouponByUUID(uuidForMocks);
        UUID actualUUID = actualUserCoupon.map(CouponDto::uuid).orElse(null);
        Optional<CouponDto> optionalCoupon = numberReceiverFacade.getUserCouponByUUID(uuidForMocks);

        // then
        assertThat(actualUUID).isEqualTo(uuidForMocks);
        assertThat(optionalCoupon).isEmpty();
    }

    @Test
    @DisplayName("should delete all expired coupons")
    void deleteAllExpiredCoupons_givenCurrentTime_allExpiredCouponsAreRemoved() {
        // given
        CouponDto expectedExpiredCoupon1 = numberReceiverFacade.inputNumbers(List.of(1, 2, 3, 4, 5, 6));
        CouponDto expectedExpiredCoupon2 = numberReceiverFacade.inputNumbers(List.of(7, 8, 9, 10, 11, 12));
        Duration expirationInDays = Duration.ofDays(365 * 2);
        LocalDateTime timeAfterExpirationDuration = timeGeneratorFacade.getDrawDateAndTime().plusDays(expirationInDays.toDays() + 1);
        Clock clockAfterExpiration = sampleClock(timeAfterExpirationDuration.toLocalDate(), timeAfterExpirationDuration.toLocalTime());
        TimeGeneratorFacade timeGeneratorFacadeAfter = timeGeneratorConfig.createForTest(clockAfterExpiration);
        NumberReceiverFacade numberReceiverFacadeAfter = numberReceiverConfig.createForTests(uuidGenerator, receiverCouponRepository, timeGeneratorFacadeAfter);
        seedSomeCouponsToTestDB(2);

        // when
        List<CouponDto> deletedCoupons = numberReceiverFacadeAfter.deleteAllExpiredCoupons();

        // then
        assertThat(deletedCoupons).contains(expectedExpiredCoupon1, expectedExpiredCoupon2);

    }

    private void seedSomeCouponsToTestDB(int amount) {
        for (int i = 0; i < amount; i++) {
            numberReceiverFacade.inputNumbers(List.of(1, 2, 3, 4, 5, 6));
        }
    }

    private NumberReceiverFacade getNumberReceiverFacadeWithMockedUUID(UUID uuid) {
        UUIDGenerator mockedUuidGenerator = getMockedUUIDGenerator(uuid);
        return numberReceiverConfig.createForTests(mockedUuidGenerator, receiverCouponRepository, timeGeneratorFacade);
    }


}
