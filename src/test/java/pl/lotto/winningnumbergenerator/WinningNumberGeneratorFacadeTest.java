package pl.lotto.winningnumbergenerator;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pl.lotto.timegenerator.SampleClock;
import pl.lotto.timegenerator.TimeGeneratorConfiguration;
import pl.lotto.timegenerator.TimeGeneratorFacade;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class WinningNumberGeneratorFacadeTest implements SampleClock {

    private final TimeGeneratorConfiguration timeGeneratorConfig = new TimeGeneratorConfiguration();
    private final LocalTime defaultCurrentTimeForTests = LocalTime.of(8, 0);
    private final WinningNumberGeneratorConfiguration winningNumberGeneratorConfig = new WinningNumberGeneratorConfiguration();

    private final List<Integer> defaultExpectedWinningNumbers = List.of(1, 2, 3, 4, 5, 6);
    private WinningNumberRepository winningNumberRepository = new WinningNumberRepository();

    @AfterEach
    void tearDown() {
        winningNumberRepository = new WinningNumberRepository();
    }

    static Stream<Arguments> inputCurrentDates() {
        return Stream.of( //TODO sensitive to day of week and time value in time generator configuration
                //Arguments.of(current point in time)
                Arguments.of(LocalDate.of(2022, 8, 8)),
                Arguments.of(LocalDate.of(2022, 8, 31))
        );
    }

    @ParameterizedTest
    @MethodSource("inputCurrentDates")
    @DisplayName("should return 6 numbers sets twice when current date is before next draw date or at the same day as draw day")
    void getWinningNumbers_givenCurrentDateBeforeNextDrawDate_returnsWinningNumbers(LocalDate currentDate) {
        // given
        Clock clockForTests = sampleClock(currentDate, defaultCurrentTimeForTests);
        TimeGeneratorFacade timeGeneratorFacade = timeGeneratorConfig.createForTest(clockForTests);
        WinningNumberGeneratorFacade winningNumberGeneratorFacade = winningNumberGeneratorConfig.createForTests(timeGeneratorFacade, winningNumberRepository);

        // when
        List<Integer> actualWinningNumbers = winningNumberGeneratorFacade.getWinningNumbers();

        // then
        assertThat(actualWinningNumbers).isEqualTo(defaultExpectedWinningNumbers);
    }

}
