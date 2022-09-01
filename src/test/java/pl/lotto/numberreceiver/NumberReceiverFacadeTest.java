package pl.lotto.numberreceiver;

import java.time.*;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.lotto.numberreceiver.dto.UserCouponDTO;
import pl.lotto.temporalgenerator.SampleClock;
import pl.lotto.temporalgenerator.TemporalGeneratorConfiguration;
import pl.lotto.temporalgenerator.TemporalGeneratorFacade;

import static org.assertj.core.api.Assertions.assertThat;

class NumberReceiverFacadeTest implements SampleClock {

    final private TemporalGeneratorConfiguration timeGeneratorConfig = new TemporalGeneratorConfiguration();
    final private NumberReceiverConfiguration numberReceiverConfig = new NumberReceiverConfiguration();
    private TemporalGeneratorFacade temporalGeneratorFacade;
    private final UUIDGenerator uuidGenerator = new UUIDGenerator();

    @BeforeEach
    void setUp() {
        DayOfWeek dayOfWeek = DayOfWeek.SATURDAY;
        LocalTime drawTime = LocalTime.of(8, 15);
        Duration expirationDays = Duration.ofDays(365 * 2);
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();
        Clock clockForTests = sampleClock(currentDate, currentTime);
        temporalGeneratorFacade = timeGeneratorConfig.createForTest(dayOfWeek, drawTime, expirationDays, clockForTests);
    }

    @Test
    @DisplayName("input user numbers and returns dto with correct user numbers")
    void inputNumbers_givenInputNumbers_returnsResultDTOWithUserNumbers() {
        UserCouponRepository receiverRepository = new NumberReceiverRepositoryTestImpl();
        NumberReceiverFacade numberReceiverFacade = numberReceiverConfig.createForTests(uuidGenerator, receiverRepository, temporalGeneratorFacade);
        List<Integer> numbersFromUser = List.of(1, 2, 3, 4, 5, 6);

        // when
        UserCouponDTO result = numberReceiverFacade.inputNumbers(numbersFromUser);
        List<Integer> actualUserNumbers = result.typedNumbers();

        // then
        assertThat(actualUserNumbers).isEqualTo(numbersFromUser);
    }


}
