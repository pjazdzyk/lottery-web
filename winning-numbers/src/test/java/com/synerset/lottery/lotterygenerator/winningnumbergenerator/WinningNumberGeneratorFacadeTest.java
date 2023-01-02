package com.synerset.lottery.lotterygenerator.winningnumbergenerator;

import com.synerset.lottery.lotterygenerator.winningnumbergenerator.dto.WinningNumberStatus;
import com.synerset.lottery.lotterygenerator.winningnumbergenerator.dto.WinningNumbersResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class WinningNumberGeneratorFacadeTest implements MockedRandomGenerator {

    private final LocalDateTime sampleDrawDate = LocalDateTime.of(2022, 8, 12, 12, 0);
    private final WinningPropertyConfigurable winningConfig = new WinningPropertyPropertyConfigTest(1, 99, 6);
    private final RandomNumbersGenerator randomNumbersGenerator = new RandomNumbersGenerator(winningConfig);
    private final WinningNumberRepository winningNumberRepository = new WinningNumberRepositoryInMemory();
    private final WinningNumberGeneratorConfiguration winningNumberGeneratorConfig = new WinningNumberGeneratorConfiguration();
    private final WinningNumberGeneratorFacade winningNumberGeneratorFacade = winningNumberGeneratorConfig.createForTests(randomNumbersGenerator, winningNumberRepository);


    @Test
    @DisplayName("should return winning numbers when draw date is provided")
    void getWinningNumbersForDate_givenDrawDate_shouldReturnWinningNumbers() {
        // given
        List<Integer> expectedWinningNumbers = List.of(1, 2, 3, 4, 5, 6);
        RandomNumbersGenerator mockedRandomNumbersGenerator = getMockedNumberGenerator(expectedWinningNumbers);
        WinningNumberGeneratorFacade winningNumberGeneratorFacade = winningNumberGeneratorConfig
                .createForTests(mockedRandomNumbersGenerator, winningNumberRepository);
        winningNumberGeneratorFacade.generateWinningNumbers(sampleDrawDate);

        // when
        WinningNumbersResponseDto actualWinningNumbersResponseDto = winningNumberGeneratorFacade.retrieveWinningNumbersForDrawDate(sampleDrawDate);

        // then
        assertThat(actualWinningNumbersResponseDto).isNotNull();
        assertThat(actualWinningNumbersResponseDto.drawDate()).isEqualTo(sampleDrawDate);
        assertThat(actualWinningNumbersResponseDto.winningNumbers()).isEqualTo(expectedWinningNumbers);
        assertThat(actualWinningNumbersResponseDto.status()).isEqualTo(WinningNumberStatus.LOADED_FROM_DB);

    }

    @Test
    @DisplayName("should return not found dto when draw date is provided which is not in the database")
    void getWinningNumbersForDate_givenInvalidDrawDate_shouldReturnNotFound() {
        // given
        winningNumberGeneratorFacade.generateWinningNumbers(sampleDrawDate);

        // when
        WinningNumbersResponseDto actualWinningNumbersResponseDto = winningNumberGeneratorFacade.retrieveWinningNumbersForDrawDate(LocalDateTime.of(1900, 1, 1, 1, 1, 1));

        // then
        assertThat(actualWinningNumbersResponseDto).isNotNull();
        assertThat(actualWinningNumbersResponseDto.status()).isEqualTo(WinningNumberStatus.NOT_FOUND);
    }


    @Test
    @DisplayName("should return not found dto when winning number repository is empty")
    void getLastWinningNumbers_givenDrawDate_shouldReturnNotFoundDto() {
        // given
        // when
        WinningNumbersResponseDto actualWinningNumbersResponseDto = winningNumberGeneratorFacade.retrieveWinningNumbersForDrawDate(sampleDrawDate);

        // then
        assertThat(actualWinningNumbersResponseDto).isNotNull();
        assertThat(actualWinningNumbersResponseDto.status()).isEqualTo(WinningNumberStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("should remove winning numbers from repository for a specified draw date when draw date is provided")
    void deleteWinningNumbersForDate_givenDrawDate_shouldRemoveWinningNumbersFromDb() {
        // given
        winningNumberGeneratorFacade.generateWinningNumbers(sampleDrawDate);

        // when
        WinningNumbersResponseDto actualDeletedListOfWinningNumbers = winningNumberGeneratorFacade.deleteWinningNumbersForDate(sampleDrawDate);
        List<WinningNumbersResponseDto> actualRemainingListOfWinningNumbers = winningNumberGeneratorFacade.getAllWinningNumbers();

        // then
        assertThat(actualDeletedListOfWinningNumbers).isNotNull();
        assertThat(actualRemainingListOfWinningNumbers).isNotNull();
        assertThat(actualDeletedListOfWinningNumbers.status()).isEqualTo(WinningNumberStatus.DELETED);
        assertThat(actualRemainingListOfWinningNumbers).isEmpty();
    }

    @Test
    @DisplayName("should return all winning number for each draw date")
    void getAllWinningNumbers_shouldReturnAllWinningNumbersForEachDrawDate() {
        // given
        winningNumberGeneratorFacade.generateWinningNumbers(sampleDrawDate);
        LocalDateTime oneWeekLaterDrawTime = sampleDrawDate.plusDays(7);
        winningNumberGeneratorFacade.generateWinningNumbers(oneWeekLaterDrawTime);
        LocalDateTime twoWeekLaterDrawTime = sampleDrawDate.plusDays(14);
        winningNumberGeneratorFacade.generateWinningNumbers(twoWeekLaterDrawTime);

        // when
        List<WinningNumbersResponseDto> allWinningNumbersResponseDto = winningNumberGeneratorFacade.getAllWinningNumbers();

        // then
        int expectedWinningNumberSets = 3;
        assertThat(allWinningNumbersResponseDto).isNotNull();
        assertThat(allWinningNumbersResponseDto).hasSize(expectedWinningNumberSets);
        assertThat(allWinningNumbersResponseDto).allMatch(winningDto -> winningDto.status() == WinningNumberStatus.LOADED_FROM_DB);
    }

    @Test
    @DisplayName("should return not found dto when there are no winning results drawn yet")
    void getAllWinningNumbers_shouldReturnNotFoundDtoIfNoWinningNumbersAreStoredInRepository() {
        // given
        // when
        List<WinningNumbersResponseDto> allWinningNumbersResponseDto = winningNumberGeneratorFacade.getAllWinningNumbers();

        // then
        assertThat(allWinningNumbersResponseDto).isNotNull();
        assertThat(allWinningNumbersResponseDto).isEmpty();
    }

}
