package pl.lotto.numberreceiver;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.lotto.numberreceiver.dto.InputStatus;
import pl.lotto.numberreceiver.dto.ReceiverDto;
import pl.lotto.timegenerator.TimeGeneratorFacade;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class NumberReceiverFacadeTest implements MockedUUIDGenerator, MockedTimeGeneratorFacade {

    private final UUID uuidForMocks = UUID.fromString("ef241277-64a2-457a-beea-a4c589803a26");
    private final ReceiverUuidGenerator receiverUuidGenerator = new ReceiverUuidGenerator();
    private CouponRepository receiverCouponRepository = new NumberReceiverRepositoryInMemory();
    private final TimeGeneratorFacade mockedTimeGeneratorFacade = createMockedTimeGeneratorFacadeWithDefaultDates();
    final private NumberReceiverConfiguration numberReceiverConfig = new NumberReceiverConfiguration();
    final private InputConfigurable inputConfig = new InputPropertyConfigTest(1,99,6);
    private NumberReceiverFacade numberReceiverFacade = numberReceiverConfig.createForTests(receiverUuidGenerator, receiverCouponRepository, mockedTimeGeneratorFacade, inputConfig);

    @AfterEach
    void tearDown() {
        resetTimeFacadeToDefaultDates(mockedTimeGeneratorFacade);
        receiverCouponRepository = new NumberReceiverRepositoryInMemory();
        numberReceiverFacade = numberReceiverConfig.createForTests(receiverUuidGenerator, receiverCouponRepository, mockedTimeGeneratorFacade, inputConfig);
    }

    @Test
    @DisplayName("should return dto with invalid status when input numbers are not provided (null), numbers are not saved")
    void inputNumbers_givenInputNumbersAsNull_returnDtoWithInvalidStatus() {
        // given
        List<Integer> inputNumbers = null;

        // when
        ReceiverDto actualDto = numberReceiverFacade.inputNumbers(inputNumbers);
        List<ReceiverDto> actualRepositoryItems = numberReceiverFacade.getAllCoupons();

        // then
        assertThat(actualDto.status()).isEqualTo(InputStatus.INVALID);
        assertThat(actualRepositoryItems).isEmpty();
    }

    @Test
    @DisplayName("should return dto with invalid status when input number list have incorrect size, numbers are not saved")
    void inputNumbers_givenInputNumbersWithIncorrectSize_returnsDtoWithInvalidStatus() {
        // given
        List<Integer> notEnoughNumbers = List.of(1, 2, 3, 4, 5);
        List<Integer> toManyNumbers = List.of(1, 2, 3, 4, 5, 6, 7);

        // when
        ReceiverDto actualNotEnough = numberReceiverFacade.inputNumbers(notEnoughNumbers);
        ReceiverDto actualToMany = numberReceiverFacade.inputNumbers(toManyNumbers);
        List<ReceiverDto> actualRepositoryItems = numberReceiverFacade.getAllCoupons();

        // then
        assertThat(actualNotEnough.uuid()).isNull();
        assertThat(actualNotEnough.status()).isEqualTo(InputStatus.INVALID);
        assertThat(actualToMany.uuid()).isNull();
        assertThat(actualToMany.status()).isEqualTo(InputStatus.INVALID);
        assertThat(actualRepositoryItems).isEmpty();
    }

    @Test
    @DisplayName("should return dto with invalid status when input number list contains negative number")
    void inputNumbers_givenInputNumbersContainsNegativeValue_returnDtoWithInvalidStatus() {
        // given
        List<Integer> inputNumbers = List.of(1, 2, -3, 4, 5, 6);

        // when
        ReceiverDto actualDto = numberReceiverFacade.inputNumbers(inputNumbers);
        List<ReceiverDto> actualRepositoryItems = numberReceiverFacade.getAllCoupons();

        // then
        assertThat(actualDto.status()).isEqualTo(InputStatus.INVALID);
        assertThat(actualRepositoryItems).isEmpty();
    }

    @Test
    @DisplayName("should return dto with invalid status when input number contains numbers outside game rules boundary")
    void inputNumbers_givenInputNumbersWithNumbersOutsideBoundary_returnsDtoWithInvalidStatus() {
        // given
        List<Integer> withNumberBelowLimit = List.of(0, 2, 3, 4, 5, 6);
        List<Integer> withNumberAboveLimit = List.of(1, 2, 3, 4, 100, 6);

        // when
        ReceiverDto actualBelowLimitDto = numberReceiverFacade.inputNumbers(withNumberBelowLimit);
        ReceiverDto actualAboveLimitDto = numberReceiverFacade.inputNumbers(withNumberAboveLimit);
        List<ReceiverDto> actualRepositoryItems = numberReceiverFacade.getAllCoupons();

        // then
        assertThat(actualBelowLimitDto.uuid()).isNull();
        assertThat(actualBelowLimitDto.status()).isEqualTo(InputStatus.INVALID);
        assertThat(actualAboveLimitDto.uuid()).isNull();
        assertThat(actualAboveLimitDto.status()).isEqualTo(InputStatus.INVALID);
        assertThat(actualRepositoryItems).isEmpty();
    }

    @Test
    @DisplayName("should save coupon when user input numbers are provided")
    void inputNumbers_givenInputNumbers_returnsResultDTOWithUserNumbers() {
        // given
        List<Integer> numbersFromUser = List.of(1, 2, 3, 4, 5, 6);

        // when
        ReceiverDto actualCoupon = numberReceiverFacade.inputNumbers(numbersFromUser);

        // then
        ReceiverDto expectedReceiverDto = numberReceiverFacade.getUserCouponByUUID(actualCoupon.uuid());
        assertThat(actualCoupon).isEqualTo(expectedReceiverDto);
        assertThat(actualCoupon.status()).isEqualTo(InputStatus.SAVED);
    }

    @Test
    @DisplayName("should return list of coupons for specific draw date when draw date is provided")
    void getUserCouponsListForDrawDate_givenDrawDate_returnsUserCouponForExpectedDrawDate() {
        // given (8 August by default)
        List<ReceiverDto> initialCoupons = seedSomeCouponsToTestDB(numberReceiverFacade, 1);
        // Time has passed, new draw date is
        LocalDateTime laterDrawDate = sampleDrawDate.plusDays(7);
        when(mockedTimeGeneratorFacade.getDrawDateAndTime()).thenReturn(laterDrawDate);
        seedSomeCouponsToTestDB(numberReceiverFacade, 1);

        // when
        List<ReceiverDto> actualCouponsForSpecifiedDrawDate = numberReceiverFacade.getUserCouponListForDrawDate(laterDrawDate);

        // then
        assertThat(actualCouponsForSpecifiedDrawDate).doesNotContainAnyElementsOf(initialCoupons);
        assertThat(actualCouponsForSpecifiedDrawDate).hasSize(1);
        assertThat(actualCouponsForSpecifiedDrawDate.get(0).status()).isEqualTo(InputStatus.SAVED);

    }

    @Test
    @DisplayName("should return coupon when coupon UUID is provided")
    void getUserCouponByUUID_givenUUID_returnsUserCouponForExpectedUUID() {
        // given
        NumberReceiverFacade numberReceiverFacade = getNumberReceiverFacadeWithMockedUUID(uuidForMocks);
        numberReceiverFacade.inputNumbers(List.of(1, 2, 3, 4, 5, 6));

        // when
        ReceiverDto actualReceiverDto = numberReceiverFacade.getUserCouponByUUID(uuidForMocks);

        // then
        assertThat(actualReceiverDto.uuid()).isEqualTo(uuidForMocks);
        assertThat(actualReceiverDto.status()).isEqualTo(InputStatus.SAVED);
    }

    @Test
    @DisplayName("should delete coupon when coupon UUID is provided")
    void deleteUserCouponByUUID_givenUUID_returnDeletedCouponForExpectedUUID() {
        // given
        NumberReceiverFacade numberReceiverFacade = getNumberReceiverFacadeWithMockedUUID(uuidForMocks);
        numberReceiverFacade.inputNumbers(List.of(1, 2, 3, 4, 5, 6));

        // when
        ReceiverDto actualDeletedReceiverDto = numberReceiverFacade.deleteUserCouponByUUID(uuidForMocks);

        // then
        assertThat(actualDeletedReceiverDto.uuid()).isEqualTo(uuidForMocks);
        assertThat(actualDeletedReceiverDto.status()).isEqualTo(InputStatus.DELETED);
    }

    private List<ReceiverDto> seedSomeCouponsToTestDB(NumberReceiverFacade numberReceiverFacade, int amount) {
        List<ReceiverDto> coupons = new ArrayList<>(amount);
        for (int i = 0; i < amount; i++) {
            ReceiverDto receiverDto = numberReceiverFacade.inputNumbers(List.of(1, 2, 3, 4, 5, 6));
            coupons.add(receiverDto);
        }
        return coupons;
    }

    private NumberReceiverFacade getNumberReceiverFacadeWithMockedUUID(UUID uuid) {
        ReceiverUuidGenerator mockedReceiverUuidGenerator = getMockedUUIDGenerator(uuid);
        return numberReceiverConfig.createForTests(mockedReceiverUuidGenerator, receiverCouponRepository, mockedTimeGeneratorFacade, inputConfig);
    }

}
