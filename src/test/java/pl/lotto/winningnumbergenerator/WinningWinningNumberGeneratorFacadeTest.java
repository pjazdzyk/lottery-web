package pl.lotto.winningnumbergenerator;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.lotto.timegenerator.AdjustableClock;
import pl.lotto.timegenerator.TimeGeneratorConfiguration;
import pl.lotto.timegenerator.TimeGeneratorFacade;
import pl.lotto.winningnumbergenerator.dto.WinningNumbersDto;

import java.time.*;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class WinningWinningNumberGeneratorFacadeTest implements MockedNumberGenerator {

    private final TimeGeneratorConfiguration timeGeneratorConfig = new TimeGeneratorConfiguration();
    private final LocalDate sampleInitialDate = LocalDate.of(2022, 8, 10);
    private final LocalTime sampleInitialTime = LocalTime.of(12, 11);
    private final DayOfWeek drawDayOfWeek = DayOfWeek.FRIDAY;
    private final LocalTime drawTime = LocalTime.of(12, 10);
    private final Duration expirationInDays = Duration.ofDays(365 * 2);
    private final AdjustableClock sampleClockForTests = AdjustableClock.fromLocalDateAndLocalTime(sampleInitialDate, sampleInitialTime, ZoneId.systemDefault());
    private final RandomGenerator randomGenerator = new RandomGenerator();
    private final WinningNumberGeneratorConfiguration winningNumberGeneratorConfig = new WinningNumberGeneratorConfiguration();
    private WinningNumberRepository winningNumberRepository = new WinningNumberRepositoryImpl();
    private TimeGeneratorFacade timeGeneratorFacade = timeGeneratorConfig.createForTest(sampleClockForTests, drawDayOfWeek, drawTime, expirationInDays);
    private WinningNumberGeneratorFacade winningNumberGeneratorFacade = winningNumberGeneratorConfig.createForTests(randomGenerator, timeGeneratorFacade, winningNumberRepository);

    @AfterEach
    void tearDown() {
        winningNumberRepository = new WinningNumberRepositoryImpl();
        timeGeneratorFacade = timeGeneratorConfig.createForTest(sampleClockForTests, drawDayOfWeek, drawTime, expirationInDays);
        winningNumberGeneratorFacade = winningNumberGeneratorConfig.createForTests(randomGenerator, timeGeneratorFacade, winningNumberRepository);
    }

    @Test
    @DisplayName("should return generated winning numbers when numbers was already drawn before but they are still valid")
    void retrieveLastWinningNumbers_givenCurrentDateBeforeDrawDateNumbersWereGeneratedBefore_returnsWinningNumbers() {
        // given
        winningNumberGeneratorFacade.runLottery();
        // advancing in time by 1 day
        sampleClockForTests.plusDays(1);

        // when
        winningNumberGeneratorFacade.runLottery();
        Optional<WinningNumbersDto> actualWinningNumbersOptional = winningNumberGeneratorFacade.retrieveLastWinningNumbers();
        WinningNumbersDto actualWinningNumbers = actualWinningNumbersOptional.orElse(null);

        // then
        Optional<WinningNumbersDto> expectedWinningNumbersOptional = winningNumberGeneratorFacade.retrieveLastWinningNumbers();
        WinningNumbersDto expectedWinningNumbers = expectedWinningNumbersOptional.orElse(null);
        assertThat(actualWinningNumbers).isEqualTo(expectedWinningNumbers);
    }

    @Test
    @DisplayName("should generate new winning numbers when given date is after a draw date")
    void retrieveLastWinningNumbers_givenCurrentDateAfterDrawDateNumbersWereGeneratedBefore_returnsNewWinningNumbers() {
        // given
        winningNumberGeneratorFacade.runLottery();
        Optional<WinningNumbersDto> oldWinningNumbersOptional = winningNumberGeneratorFacade.retrieveLastWinningNumbers();
        WinningNumbersDto previousWinningNumbers = oldWinningNumbersOptional.orElse(null);
        // advancing in time by 10 days
        sampleClockForTests.plusDays(10);

        // when
        winningNumberGeneratorFacade.runLottery();
        Optional<WinningNumbersDto> actualWinningNumbersOptional = winningNumberGeneratorFacade.retrieveLastWinningNumbers();
        WinningNumbersDto actualWinningNumbers = actualWinningNumbersOptional.orElse(null);

        // then
        assertThat(actualWinningNumbers).isNotEqualTo(previousWinningNumbers);

    }

    @Test
    @DisplayName("should remove winning numbers from repository for a specified draw date when draw date is provided")
    void deleteWinningNumbersForDate_givenDrawDate_removesWinningNumbersFromDb() {
        // given
        TimeGeneratorFacade timeGeneratorFacade = timeGeneratorConfig.createForTest(sampleClockForTests, drawDayOfWeek, drawTime, expirationInDays);
        WinningNumberGeneratorFacade winningNumberGeneratorFacade = winningNumberGeneratorConfig.createForTests(randomGenerator, timeGeneratorFacade, winningNumberRepository);
        LocalDateTime drawDate = timeGeneratorFacade.getDrawDateAndTime();
        winningNumberGeneratorFacade.runLottery();

        // when
        winningNumberGeneratorFacade.deleteWinningNumbersForDate(drawDate);
        List<WinningNumbersDto> actualListOfWinningNumbers = winningNumberGeneratorFacade.getAllWinningNumbers();

        // then
        assertThat(actualListOfWinningNumbers).isEmpty();
    }


}
