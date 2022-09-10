package pl.lotto.numberreceiver;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.lotto.numberreceiver.dto.CouponDto;
import pl.lotto.timegenerator.AdjustableClock;
import pl.lotto.timegenerator.TimeGeneratorConfiguration;
import pl.lotto.timegenerator.TimeGeneratorFacade;

import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class NumberReceiverFacadeTest implements MockedUUIDGenerator {

    final private TimeGeneratorConfiguration timeGeneratorConfig = new TimeGeneratorConfiguration();
    final private NumberReceiverConfiguration numberReceiverConfig = new NumberReceiverConfiguration();
    private final UUIDGenerator uuidGenerator = new UUIDGenerator();
    private final LocalDate sampleDateTests = LocalDate.of(2022, 8, 8);
    private final LocalTime sampleTimeTests = LocalTime.of(10, 10);
    private final AdjustableClock sampleClockForTests = AdjustableClock.fromLocalDateAndLocalTime(sampleDateTests, sampleTimeTests, ZoneId.systemDefault());
    private final DayOfWeek drawDayOfWeek = DayOfWeek.FRIDAY;
    private final LocalTime drawTime = LocalTime.of(12, 10);
    private final Duration expirationInDays = Duration.ofDays(365 * 2);
    private TimeGeneratorFacade timeGeneratorFacade = timeGeneratorConfig.createForTest(sampleClockForTests, drawDayOfWeek, drawTime, expirationInDays);
    private final UUID uuidForMocks = UUID.fromString("ef241277-64a2-457a-beea-a4c589803a26");
    private CouponRepository receiverCouponRepository = new NumberReceiverRepositoryImpl();
    private NumberReceiverFacade numberReceiverFacade = numberReceiverConfig.createForTests(uuidGenerator, receiverCouponRepository, timeGeneratorFacade);

    @AfterEach
    void tearDown() {
        receiverCouponRepository = new NumberReceiverRepositoryImpl();
        sampleClockForTests.setClockFromLocalDateTime(LocalDateTime.of(sampleDateTests, sampleTimeTests));
        timeGeneratorFacade = timeGeneratorConfig.createForTest(sampleClockForTests, drawDayOfWeek, drawTime, expirationInDays);
        numberReceiverFacade = numberReceiverConfig.createForTests(uuidGenerator, receiverCouponRepository, timeGeneratorFacade);
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
        // given (8 August by default)
        List<CouponDto> initialCoupons = seedSomeCouponsToTestDB(numberReceiverFacade, 1);

        // Advancing in time to (13 August)
        sampleClockForTests.plusDays(5);
        LocalDateTime laterDrawDate = timeGeneratorFacade.getDrawDateAndTime();
        seedSomeCouponsToTestDB(numberReceiverFacade, 1);

        // when
        List<CouponDto> actualCouponsForSpecifiedDrawDate = numberReceiverFacade.getUserCouponListForDrawDate(laterDrawDate);

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
        // advancing in time by duration of expiration, including days to next draw, and +1 to get beyond expiration
        LocalDateTime oldDrawDate = timeGeneratorFacade.getDrawDateAndTime();
        Duration offsetFromSampleLocalTimeToDrawDate = Duration.between(timeGeneratorFacade.getCurrentDateAndTime(), oldDrawDate);
        Duration durationBeyondExpiration = expirationInDays.plus(offsetFromSampleLocalTimeToDrawDate).plusDays(1);
        sampleClockForTests.advanceInTimeBy(durationBeyondExpiration);
        seedSomeCouponsToTestDB(numberReceiverFacade, 2);

        // when
        List<CouponDto> deletedCoupons = numberReceiverFacade.deleteAllExpiredCoupons();
        List<CouponDto> actualRemainingCoupons = numberReceiverFacade.getAllCoupons();

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
