package pl.lotto.winningnumbergenerator;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.lotto.timegenerator.SampleClock;
import pl.lotto.timegenerator.TimeGeneratorConfiguration;
import pl.lotto.timegenerator.TimeGeneratorFacade;
import pl.lotto.winningnumbergenerator.dto.WinningNumbersDto;

import java.time.*;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class WinningWinningNumberGeneratorFacadeTest implements SampleClock, MockedNumberGenerator {

    private final TimeGeneratorConfiguration timeGeneratorConfig = new TimeGeneratorConfiguration();
    private final LocalDate sampleInitialDate = LocalDate.of(2022, 8, 10);
    private final LocalTime sampleInitialTime = LocalTime.of(12, 11);
    private final DayOfWeek drawDayOfWeek = DayOfWeek.FRIDAY;
    private final LocalTime drawTime = LocalTime.of(12, 10);
    private final Duration expirationInDays = Duration.ofDays(365 * 2);
    private final Clock initialClock = sampleClock(sampleInitialDate, sampleInitialTime);
    private final RandomGenerator randomGenerator = new RandomGenerator();
    private final WinningNumberGeneratorConfiguration winningNumberGeneratorConfig = new WinningNumberGeneratorConfiguration();
    private WinningNumberRepository winningNumberRepository = new WinningNumberRepositoryImpl();

    @AfterEach
    void tearDown() {
        winningNumberRepository = new WinningNumberRepositoryImpl();
    }

    @Test
    @DisplayName("should return generated winning numbers when numbers was already drawn before but they are still valid")
    void retrieveLastWinningNumbers_givenCurrentDateBeforeDrawDateNumbersWereGeneratedBefore_returnsWinningNumbers() {
        // given
        TimeGeneratorFacade timeGeneratorFacade = timeGeneratorConfig.createForTest(initialClock, drawDayOfWeek, drawTime, expirationInDays);
        WinningNumberGeneratorFacade winningNumberGeneratorFacade = winningNumberGeneratorConfig.createForTests(randomGenerator, timeGeneratorFacade, winningNumberRepository);
        winningNumberGeneratorFacade.runLottery();
        // advancing in time by 1 day
        Clock clockOffset = sampleClock(sampleInitialDate.plusDays(1), sampleInitialTime);
        TimeGeneratorFacade timeGeneratorFacadeOffset = timeGeneratorConfig.createForTest(clockOffset, drawDayOfWeek, drawTime, expirationInDays);
        WinningNumberGeneratorFacade winningNumberGeneratorFacadeOffset = winningNumberGeneratorConfig.createForTests(randomGenerator, timeGeneratorFacadeOffset, winningNumberRepository);

        // when
        winningNumberGeneratorFacadeOffset.runLottery();
        Optional<WinningNumbersDto> actualWinningNumbersOptional = winningNumberGeneratorFacadeOffset.retrieveLastWinningNumbers();
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
        TimeGeneratorFacade timeGeneratorFacade = timeGeneratorConfig.createForTest(initialClock, drawDayOfWeek, drawTime, expirationInDays);
        WinningNumberGeneratorFacade winningNumberGeneratorFacade = winningNumberGeneratorConfig.createForTests(randomGenerator, timeGeneratorFacade, winningNumberRepository);
        winningNumberGeneratorFacade.runLottery();
        // advancing in time by 1 day
        Clock clockOffset = sampleClock(sampleInitialDate.plusDays(10), sampleInitialTime);
        TimeGeneratorFacade timeGeneratorFacadeOffset = timeGeneratorConfig.createForTest(clockOffset, drawDayOfWeek, drawTime, expirationInDays);
        WinningNumberGeneratorFacade winningNumberGeneratorFacadeOffset = winningNumberGeneratorConfig.createForTests(randomGenerator, timeGeneratorFacadeOffset, winningNumberRepository);

        // when
        winningNumberGeneratorFacadeOffset.runLottery();
        Optional<WinningNumbersDto> actualWinningNumbersOptional = winningNumberGeneratorFacadeOffset.retrieveLastWinningNumbers();
        WinningNumbersDto actualWinningNumbers = actualWinningNumbersOptional.orElse(null);

        // then
        Optional<WinningNumbersDto> expectedWinningNumbersOptional = winningNumberGeneratorFacade.retrieveLastWinningNumbers();
        WinningNumbersDto expectedWinningNumbers = expectedWinningNumbersOptional.orElse(null);
        assertThat(actualWinningNumbers).isNotEqualTo(expectedWinningNumbers);

    }

    @Test
    @DisplayName("should remove winning numbers from repository for a specified draw date when draw date is provided")
    void deleteWinningNumbersForDate_givenDrawDate_removesWinningNumbersFromDb() {
        // given
        TimeGeneratorFacade timeGeneratorFacade = timeGeneratorConfig.createForTest(initialClock, drawDayOfWeek, drawTime, expirationInDays);
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
