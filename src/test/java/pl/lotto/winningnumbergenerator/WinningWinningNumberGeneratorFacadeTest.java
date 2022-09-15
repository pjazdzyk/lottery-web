package pl.lotto.winningnumbergenerator;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.lotto.numberreceiver.MockedTimeGeneratorFacade;
import pl.lotto.timegenerator.TimeGeneratorFacade;
import pl.lotto.winningnumbergenerator.dto.WinningNumbersDto;

import java.time.*;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class WinningWinningNumberGeneratorFacadeTest implements MockedRandomGenerator, MockedTimeGeneratorFacade {

    private final RandomGeneratorRules randomGeneratorRules = new RandomGeneratorRules(1,99,6);
    private final RandomGenerator randomGenerator = new RandomGenerator(randomGeneratorRules);
    private final WinningNumberRepository winningNumberRepository = new WinningNumberRepositoryStub();
    private final TimeGeneratorFacade mockedTimeGeneratorFacade = createMockedTimeGeneratorFacadeWithDefaultDates();
    private final WinningNumberGeneratorConfiguration winningNumberGeneratorConfig = new WinningNumberGeneratorConfiguration();
    private final WinningNumberGeneratorFacade winningNumberGeneratorFacade = winningNumberGeneratorConfig.createForTests(randomGenerator, mockedTimeGeneratorFacade, winningNumberRepository);

    @AfterEach
    void tearDown() {
        resetTimeFacadeToDefaultDates(mockedTimeGeneratorFacade);
    }

    @Test
    @DisplayName("should return the same generated winning numbers when numbers was previously drawn before but they are still valid")
    void retrieveLastWinningNumbers_givenCurrentDateBeforeDrawDateNumbersWereGeneratedBefore_returnsWinningNumbers() {
        // given
        winningNumberGeneratorFacade.runLottery();
        // advancing in time by 1 day
        LocalDateTime dayLater = sampleCurrentDateTime.plusDays(1);
        when(mockedTimeGeneratorFacade.getCurrentDateAndTime()).thenReturn(dayLater);

        // when
        winningNumberGeneratorFacade.runLottery();
        Optional<WinningNumbersDto> actualWinningNumbersOptional = winningNumberGeneratorFacade.getLastWinningNumbers();
        WinningNumbersDto actualWinningNumbers = actualWinningNumbersOptional.orElse(null);

        // then
        Optional<WinningNumbersDto> expectedWinningNumbersOptional = winningNumberGeneratorFacade.getLastWinningNumbers();
        WinningNumbersDto expectedWinningNumbers = expectedWinningNumbersOptional.orElse(null);
        assertThat(actualWinningNumbers).isEqualTo(expectedWinningNumbers);
    }

    @Test
    @DisplayName("should generate new winning numbers when given date is after a draw date")
    void retrieveLastWinningNumbers_givenCurrentDateAfterDrawDateNumbersWereGeneratedBefore_returnsNewWinningNumbers() {
        // given
        winningNumberGeneratorFacade.runLottery();
        Optional<WinningNumbersDto> oldWinningNumbersOptional = winningNumberGeneratorFacade.getLastWinningNumbers();
        WinningNumbersDto previousWinningNumbers = oldWinningNumbersOptional.orElse(null);
        // advancing in time by 10 days
        LocalDateTime tenDaysLater = sampleCurrentDateTime.plusDays(10);
        LocalDateTime laterDrawDate = sampleDrawDate.plusDays(14);
        when(mockedTimeGeneratorFacade.getCurrentDateAndTime()).thenReturn(tenDaysLater);
        when(mockedTimeGeneratorFacade.getDrawDateAndTime()).thenReturn(laterDrawDate);
        winningNumberGeneratorFacade.runLottery();

        // when
        Optional<WinningNumbersDto> actualWinningNumbersOptional = winningNumberGeneratorFacade.getLastWinningNumbers();
        WinningNumbersDto actualWinningNumbers = actualWinningNumbersOptional.orElse(null);

        // then
        assertThat(actualWinningNumbers).isNotEqualTo(previousWinningNumbers);

    }

    @Test
    @DisplayName("should remove winning numbers from repository for a specified draw date when draw date is provided")
    void deleteWinningNumbersForDate_givenDrawDate_removesWinningNumbersFromDb() {
        // given
        winningNumberGeneratorFacade.runLottery();

        // when
        winningNumberGeneratorFacade.deleteWinningNumbersForDate(sampleDrawDate);
        List<WinningNumbersDto> actualListOfWinningNumbers = winningNumberGeneratorFacade.getAllWinningNumbers();

        // then
        assertThat(actualListOfWinningNumbers).isEmpty();
    }


}
