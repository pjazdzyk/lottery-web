package pl.lotto.winningnumbergenerator;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pl.lotto.timegenerator.SampleClock;
import pl.lotto.timegenerator.TimeGeneratorConfiguration;
import pl.lotto.timegenerator.TimeGeneratorFacade;
import pl.lotto.winningnumbergenerator.dto.WinningNumbersDto;

import java.time.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class WinningWinningNumberGeneratorFacadeTest implements SampleClock, MockedNumberGenerator {

    private final TimeGeneratorConfiguration timeGeneratorConfig = new TimeGeneratorConfiguration();
    private final LocalTime defaultTimeForTests = LocalTime.of(8, 0);
    private final WinningNumberGeneratorConfiguration winningNumberGeneratorConfig = new WinningNumberGeneratorConfiguration();
    private final DayOfWeek drawDayOfWeek = DayOfWeek.FRIDAY;
    private final LocalTime drawTime = LocalTime.of(12, 10);
    private final Duration expirationInDays = Duration.ofDays(365 * 2);
    private WinningNumberRepository winningNumberRepository = new WinningNumberRepositoryImpl();

    @AfterEach
    void tearDown() {
        winningNumberRepository = new WinningNumberRepositoryImpl();
    }

    static Stream<Arguments> inputCurrentDates() {
        return Stream.of(
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
        Clock clockForTests = sampleClock(currentDate, defaultTimeForTests);
        List<Integer> sampleWinningNumbers = List.of(1, 2, 3, 4, 5, 6);
        RandomGenerator mockedRandomGenerator = getMockedNumberGenerator(sampleWinningNumbers);
        TimeGeneratorFacade timeGeneratorFacade = timeGeneratorConfig.createForTest(clockForTests, drawDayOfWeek, drawTime, expirationInDays);
        WinningNumberGeneratorFacade winningNumberGeneratorFacade = winningNumberGeneratorConfig.createForTests(mockedRandomGenerator, timeGeneratorFacade, winningNumberRepository);

        // when
        winningNumberGeneratorFacade.runLottery();
        Optional<WinningNumbersDto> optionalWinningNumbers = winningNumberGeneratorFacade.retrieveLAstWinningNumbers();
        WinningNumbers actualWinningNumbers = optionalWinningNumbers.map(WinningNumberMapper::toWinningNumbers).orElse(null);

        // then
        WinningNumbers expectedWinningNumbers = new WinningNumbers(timeGeneratorFacade.getDrawDateAndTime(), sampleWinningNumbers);
        assertThat(actualWinningNumbers).isEqualTo(expectedWinningNumbers);

    }

}
