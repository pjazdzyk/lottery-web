package pl.lotto.numberreceiver;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.lotto.numberreceiver.dto.CouponDto;
import pl.lotto.timegenerator.SampleClock;
import pl.lotto.timegenerator.TimeGeneratorConfiguration;
import pl.lotto.timegenerator.TimeGeneratorFacade;

import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class NumberReceiverFacadeTest implements SampleClock, MockedUUIDGenerator {

    final private TimeGeneratorConfiguration timeGeneratorConfig = new TimeGeneratorConfiguration();
    final private NumberReceiverConfiguration numberReceiverConfig = new NumberReceiverConfiguration();
    private final UUIDGenerator uuidGenerator = new UUIDGenerator();
    private final Clock defaultClockForTests = sampleClock(LocalDate.of(2022, 8, 10), LocalTime.of(8, 0));
    private final DayOfWeek drawDayOfWeek = DayOfWeek.FRIDAY;
    private final LocalTime drawTime = LocalTime.of(12, 10);
    private final Duration expirationInDays = Duration.ofDays(365 * 2);
    private final TimeGeneratorFacade timeGeneratorFacade = timeGeneratorConfig.createForTest(defaultClockForTests, drawDayOfWeek, drawTime, expirationInDays);
    private CouponRepository receiverCouponRepository = new NumberReceiverRepositoryImpl();
    private final NumberReceiverFacade numberReceiverFacade = numberReceiverConfig.createForTests(uuidGenerator, receiverCouponRepository, timeGeneratorFacade);
    private final UUID uuidForMocks = UUID.fromString("ef241277-64a2-457a-beea-a4c589803a26");

    @AfterEach
    void tearDown() {
        receiverCouponRepository = new NumberReceiverRepositoryImpl();
    }

    @Test
    @DisplayName("should save coupon when user input numbers are provided")
    void inputNumbers_givenInputNumbers_returnsResultDTOWithUserNumbers() {
        // given
        List<Integer> numbersFromUser = List.of(1, 2, 3, 4, 5, 6);

        // when
        CouponDto actualCoupon = numberReceiverFacade.inputNumbers(numbersFromUser);

        // then
        Optional<CouponDto> expectedCouponDtoOptional = numberReceiverFacade.getUserCouponByUUID(actualCoupon.uuid());
        CouponDto expectedCouponDto = expectedCouponDtoOptional.orElse(null);
        assertThat(actualCoupon).isEqualTo(expectedCouponDto);
    }

    @Test
    @DisplayName("should return list of coupons for specific draw date when draw date is provided")
    void getUserCouponsListForDrawDate_givenDrawDate_returnsUserCouponForExpectedDrawDate() {
        // given (10 August)
        List<CouponDto> initialCoupons = seedSomeCouponsToTestDB(numberReceiverFacade, 10);
        // Advancing in time to (13 August)
        Clock clock3DaysLater = sampleClock(LocalDate.of(2022, 8, 14), LocalTime.of(8, 0));
        TimeGeneratorFacade timeGeneratorFacadeOffset = timeGeneratorConfig.createForTest(clock3DaysLater, drawDayOfWeek, drawTime, expirationInDays);
        NumberReceiverFacade numberReceiverFacadeOffset = numberReceiverConfig.createForTests(uuidGenerator, receiverCouponRepository, timeGeneratorFacadeOffset);
        LocalDateTime laterDrawDate = timeGeneratorFacadeOffset.getDrawDateAndTime();
        List<CouponDto> couponsAddedLater = seedSomeCouponsToTestDB(numberReceiverFacadeOffset, 5);

        // when
        List<CouponDto> actualCouponsForSpecifiedDrawDate = numberReceiverFacadeOffset.getUserCouponListForDrawDate(laterDrawDate);

        // then
        assertThat(actualCouponsForSpecifiedDrawDate).doesNotContainAnyElementsOf(initialCoupons);

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
        Optional<CouponDto> deletedCouponOptional = numberReceiverFacade.deleteUserCouponByUUID(uuidForMocks);
        UUID deletedUUID = deletedCouponOptional.map(CouponDto::uuid).orElse(null);

        // then
        Optional<CouponDto> expectedDeletedCouponOptional = numberReceiverFacade.getUserCouponByUUID(uuidForMocks);
        assertThat(deletedUUID).isEqualTo(uuidForMocks);
        assertThat(expectedDeletedCouponOptional).isEmpty();
    }

    @Test
    @DisplayName("should delete all expired coupons")
    void deleteAllExpiredCoupons_givenCurrentTime_allExpiredCouponsAreRemoved() {
        // given
        seedSomeCouponsToTestDB(numberReceiverFacade, 4);
        LocalDateTime oldDrawDate = timeGeneratorFacade.getDrawDateAndTime();
        // advancing in time by duration of expiration +1 day
        LocalDateTime timeAfterExpirationDuration = timeGeneratorFacade.getDrawDateAndTime().plusDays(expirationInDays.toDays() + 1);
        Clock clockAfterExpiration = sampleClock(timeAfterExpirationDuration.toLocalDate(), timeAfterExpirationDuration.toLocalTime());
        TimeGeneratorFacade timeGeneratorFacadeOffset = timeGeneratorConfig.createForTest(clockAfterExpiration, drawDayOfWeek, drawTime, expirationInDays);
        NumberReceiverFacade numberReceiverFacadeOffset = numberReceiverConfig.createForTests(uuidGenerator, receiverCouponRepository, timeGeneratorFacadeOffset);
        seedSomeCouponsToTestDB(numberReceiverFacadeOffset, 2);

        // when
        List<CouponDto> deletedCoupons = numberReceiverFacadeOffset.deleteAllExpiredCoupons();
        List<CouponDto> actualRemainingCoupons = numberReceiverFacadeOffset.getAllCoupons();

        // then
        assertThat(actualRemainingCoupons).doesNotContainAnyElementsOf(deletedCoupons);

    }

    private List<CouponDto> seedSomeCouponsToTestDB(NumberReceiverFacade numberReceiverFacade, int amount) {
        List<CouponDto> coupons = new ArrayList<>(amount);
        for (int i = 0; i < amount; i++) {
            CouponDto couponDto = numberReceiverFacade.inputNumbers(List.of(1, 2, 3, 4, 5, 6));
            coupons.add(couponDto);
        }
        return coupons;
    }

    private NumberReceiverFacade getNumberReceiverFacadeWithMockedUUID(UUID uuid) {
        UUIDGenerator mockedUuidGenerator = getMockedUUIDGenerator(uuid);
        return numberReceiverConfig.createForTests(mockedUuidGenerator, receiverCouponRepository, timeGeneratorFacade);
    }


}
