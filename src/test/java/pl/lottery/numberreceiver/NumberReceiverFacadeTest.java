package pl.lottery.numberreceiver;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.lottery.numberreceiver.dto.ReceiverStatus;
import pl.lottery.numberreceiver.dto.ReceiverResponseDto;
import pl.lottery.numberreceiver.exceptions.InvalidInputNumbersException;
import pl.lottery.timegenerator.TimeGeneratorFacade;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

class NumberReceiverFacadeTest implements MockedUUIDGenerator, MockedTimeGeneratorFacade {

    private final UUID uuidForMocks = UUID.fromString("ef241277-64a2-457a-beea-a4c589803a26");
    private final ReceiverUuidGenerator receiverUuidGenerator = new ReceiverUuidGenerator();
    private CouponRepository receiverCouponRepository = new NumberReceiverRepositoryInMemory();
    private final TimeGeneratorFacade mockedTimeGeneratorFacade = createMockedTimeGeneratorFacadeWithDefaultDates();
    final private NumberReceiverConfiguration numberReceiverConfig = new NumberReceiverConfiguration();
    final private InputConfigurable inputConfig = new InputPropertyConfigTest(1, 99, 6);
    private NumberReceiverFacade numberReceiverFacade = numberReceiverConfig.createForTests(receiverUuidGenerator, receiverCouponRepository, mockedTimeGeneratorFacade, inputConfig);

    @AfterEach
    void tearDown() {
        resetTimeFacadeToDefaultDates(mockedTimeGeneratorFacade);
        receiverCouponRepository = new NumberReceiverRepositoryInMemory();
        numberReceiverFacade = numberReceiverConfig.createForTests(receiverUuidGenerator, receiverCouponRepository, mockedTimeGeneratorFacade, inputConfig);
    }

    @Test
    @DisplayName("should throw an exception when input numbers are not provided (null), numbers are not saved")
    void inputNumbers_givenInputNumbersAsNull_throwsException() {
        // given
        // when
        // then
        assertThatThrownBy(() -> numberReceiverFacade.inputNumbers(null)).isInstanceOf(InvalidInputNumbersException.class);
    }

    @Test
    @DisplayName("should throw an exception when input number list contains to small amount of typed numbers, numbers are not saved")
    void inputNumbers_givenNotEnoughNumbers_throwsException() {
        // given
        List<Integer> notEnoughNumbers = List.of(1, 2, 3, 4, 5);

        // then
        assertThatThrownBy(() -> numberReceiverFacade.inputNumbers(notEnoughNumbers)).isInstanceOf(InvalidInputNumbersException.class);
    }

    @Test
    @DisplayName("should throw an exception when input number list have to many numbers, numbers are not saved")
    void inputNumbers_givenInputNumbersWithIncorrectSize_throwsException() {
        // given
        List<Integer> toManyNumbers = List.of(1, 2, 3, 4, 5, 6, 7);
        // when
        // then
        assertThatThrownBy(() -> numberReceiverFacade.inputNumbers(toManyNumbers)).isInstanceOf(InvalidInputNumbersException.class);
    }

    @Test
    @DisplayName("should throw an exception when input number list contains negative number")
    void inputNumbers_givenInputNumbersContainsNegativeValue_throwsException() {
        // given
        List<Integer> numbersWithNegativeValue = List.of(1, 2, -3, 4, 5, 6);

        // when
        // then
        assertThatThrownBy(() -> numberReceiverFacade.inputNumbers(numbersWithNegativeValue)).isInstanceOf(InvalidInputNumbersException.class);
    }


    @Test
    @DisplayName("should throw an exception when input number contains numbers number lower than game min constraint")
    void inputNumbers_givenInputNumbersWithNumberLowerThanMinConstraint_throwsException() {
        // given
        List<Integer> withNumberBelowLimit = List.of(0, 2, 3, 4, 5, 6);
        // when
        // then
        assertThatThrownBy(() -> numberReceiverFacade.inputNumbers(withNumberBelowLimit)).isInstanceOf(InvalidInputNumbersException.class);
    }

    @Test
    @DisplayName("should throw an exception when input number contains numbers number larger than game max constraint")
    void inputNumbers_givenInputNumbersWithNumbersLargerThanMaxConstraint_throwsException() {
        // given
        List<Integer> withNumberAboveLimit = List.of(1, 2, 3, 4, 100, 6);
        // when

        // then
        assertThatThrownBy(() -> numberReceiverFacade.inputNumbers(withNumberAboveLimit)).isInstanceOf(InvalidInputNumbersException.class);
    }

    @Test
    @DisplayName("should save coupon when user input numbers are provided")
    void inputNumbers_givenInputNumbers_returnsResultDTOWithUserNumbers() {
        // given
        List<Integer> numbersFromUser = List.of(1, 2, 3, 4, 5, 6);

        // when
        ReceiverResponseDto actualCoupon = numberReceiverFacade.inputNumbers(numbersFromUser);

        // then
        ReceiverResponseDto expectedReceiverResponseDto = numberReceiverFacade.findUserCouponByUUID(actualCoupon.uuid());
        assertThat(actualCoupon).isEqualTo(expectedReceiverResponseDto);
        assertThat(actualCoupon.status()).isEqualTo(ReceiverStatus.SAVED);
    }

    @Test
    @DisplayName("should return list of coupons for specific draw date when draw date is provided")
    void getUserCouponsListForDrawDate_givenDrawDate_returnsUserCouponForExpectedDrawDate() {
        // given (8 August by default)
        List<ReceiverResponseDto> initialCoupons = seedSomeCouponsToTestDB(numberReceiverFacade, 1);
        // Time has passed, new draw date is
        LocalDateTime laterDrawDate = sampleDrawDate.plusDays(7);
        when(mockedTimeGeneratorFacade.retrieveNextDrawDateAndTime()).thenReturn(laterDrawDate);
        seedSomeCouponsToTestDB(numberReceiverFacade, 1);

        // when
        List<ReceiverResponseDto> actualCouponsForSpecifiedDrawDate = numberReceiverFacade.findUserCouponListForDrawDate(laterDrawDate);

        // then
        assertThat(actualCouponsForSpecifiedDrawDate).doesNotContainAnyElementsOf(initialCoupons);
        assertThat(actualCouponsForSpecifiedDrawDate).hasSize(1);
        assertThat(actualCouponsForSpecifiedDrawDate.get(0).status()).isEqualTo(ReceiverStatus.SAVED);

    }

    @Test
    @DisplayName("should return coupon when coupon UUID is provided")
    void getUserCouponByUUID_givenUUID_returnsUserCouponForExpectedUUID() {
        // given
        NumberReceiverFacade numberReceiverFacade = getNumberReceiverFacadeWithMockedUUID(uuidForMocks);
        numberReceiverFacade.inputNumbers(List.of(1, 2, 3, 4, 5, 6));

        // when
        ReceiverResponseDto actualReceiverResponseDto = numberReceiverFacade.findUserCouponByUUID(uuidForMocks);

        // then
        assertThat(actualReceiverResponseDto.uuid()).isEqualTo(uuidForMocks);
        assertThat(actualReceiverResponseDto.status()).isEqualTo(ReceiverStatus.SAVED);
    }

    @Test
    @DisplayName("should delete coupon when coupon UUID is provided")
    void deleteUserCouponByUUID_givenUUID_returnDeletedCouponForExpectedUUID() {
        // given
        NumberReceiverFacade numberReceiverFacade = getNumberReceiverFacadeWithMockedUUID(uuidForMocks);
        numberReceiverFacade.inputNumbers(List.of(1, 2, 3, 4, 5, 6));

        // when
        ReceiverResponseDto actualDeletedReceiverResponseDto = numberReceiverFacade.deleteUserCouponByUUID(uuidForMocks);

        // then
        assertThat(actualDeletedReceiverResponseDto.uuid()).isEqualTo(uuidForMocks);
        assertThat(actualDeletedReceiverResponseDto.status()).isEqualTo(ReceiverStatus.DELETED);
    }

    private List<ReceiverResponseDto> seedSomeCouponsToTestDB(NumberReceiverFacade numberReceiverFacade, int amount) {
        List<ReceiverResponseDto> coupons = new ArrayList<>(amount);
        for (int i = 0; i < amount; i++) {
            ReceiverResponseDto receiverResponseDto = numberReceiverFacade.inputNumbers(List.of(1, 2, 3, 4, 5, 6));
            coupons.add(receiverResponseDto);
        }
        return coupons;
    }

    private NumberReceiverFacade getNumberReceiverFacadeWithMockedUUID(UUID uuid) {
        ReceiverUuidGenerator mockedReceiverUuidGenerator = getMockedUUIDGenerator(uuid);
        return numberReceiverConfig.createForTests(mockedReceiverUuidGenerator, receiverCouponRepository, mockedTimeGeneratorFacade, inputConfig);
    }

}
