package pl.lotto.numberreceiver;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.lotto.numberreceiver.dto.CouponDto;
import pl.lotto.timegenerator.TimeGeneratorFacade;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class NumberReceiverFacadeTest implements MockedUUIDGenerator, MockedTimeGeneratorFacade {

    private final UUID uuidForMocks = UUID.fromString("ef241277-64a2-457a-beea-a4c589803a26");
    private final UUIDGenerator uuidGenerator = new UUIDGenerator();
    private CouponRepository receiverCouponRepository = new NumberReceiverRepositoryImpl();
    private final TimeGeneratorFacade mockedTimeGeneratorFacade = createMockedTimeGeneratorFacadeWithDefaultDates();
    final private NumberReceiverConfiguration numberReceiverConfig = new NumberReceiverConfiguration();
    private  NumberReceiverFacade numberReceiverFacade = numberReceiverConfig.createForTests(uuidGenerator, receiverCouponRepository, mockedTimeGeneratorFacade);

    @AfterEach
    void tearDown() {
        resetTimeFacadeToDefaultDates(mockedTimeGeneratorFacade);
        receiverCouponRepository = new NumberReceiverRepositoryImpl();
        numberReceiverFacade = numberReceiverConfig.createForTests(uuidGenerator, receiverCouponRepository, mockedTimeGeneratorFacade);
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
        // Time has passed, new draw date is
        LocalDateTime laterDrawDate = sampleDrawDate.plusDays(7);
        when(mockedTimeGeneratorFacade.getDrawDateAndTime()).thenReturn(laterDrawDate);
        seedSomeCouponsToTestDB(numberReceiverFacade, 1);

        // when
        List<CouponDto> actualCouponsForSpecifiedDrawDate = numberReceiverFacade.getUserCouponListForDrawDate(laterDrawDate);

        // then
        assertThat(actualCouponsForSpecifiedDrawDate).doesNotContainAnyElementsOf(initialCoupons);
        assertThat(actualCouponsForSpecifiedDrawDate).hasSize(1);

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
        // advancing in time to year 2030
        when(mockedTimeGeneratorFacade.getCurrentDateAndTime()).thenReturn(sampleCurrentDateTime.plusYears(8));
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
        return numberReceiverConfig.createForTests(mockedUuidGenerator, receiverCouponRepository, mockedTimeGeneratorFacade);
    }


}
