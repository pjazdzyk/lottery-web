package pl.lotto.resultschecker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.lotto.numberreceiver.NumberReceiverFacade;
import pl.lotto.resultschecker.dto.LotteryResultsDto;
import pl.lotto.winningnumbergenerator.WinningNumberGeneratorFacade;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class ResultCheckerFacadeTest implements MockedNumberReceiverFacade, MockedWinningNumberGeneratorFacade {

    NumberReceiverFacade mockedNumberReceiverFacade = createMockedNumberReceiverFacade();
    WinningNumberGeneratorFacade mockedWinningNumbersFacade = createWinningNumberFacade();
    ResultsCheckerConfiguration resultsCheckerConfig = new ResultsCheckerConfiguration();
    ResultCheckerRepository resultCheckerRepository = new ResultsCheckerRepositoryStub();
    ResultsCheckerFacade resultsCheckerFacade = resultsCheckerConfig.createForTests(mockedNumberReceiverFacade, mockedWinningNumbersFacade, resultCheckerRepository);

    @BeforeEach
    void tearDown() {
        resultCheckerRepository = new ResultsCheckerRepositoryStub();
    }

    @Test
    @DisplayName("should generate lottery results and store in repository for a given draw date")
    void generateLotteryResultsForDrawDate_givenDrawDate_createsListOfResults() {
        // given
        // when
        int actualCount = resultsCheckerFacade.generateLotteryResultsForDrawDate(sampleDrawDateTime);

        // then
        int expectedCount = 6;
        assertThat(actualCount).isEqualTo(expectedCount);
    }

    @Test
    @DisplayName("should return lottery result when coupon uuid is provided")
    void getLotteryResultForUuid_givenUuid_returnLotteryResultDto() {
        // given
        resultsCheckerFacade.generateLotteryResultsForDrawDate(sampleDrawDateTime);

        // when
        Optional<LotteryResultsDto> resultDtoOptional = resultsCheckerFacade.getResultsForId(sampleUuid);
        LotteryResultsDto actualResult = resultDtoOptional.orElse(null);

        // then
        assertThat(actualResult).isNotNull();
        assertThat(actualResult.uuid()).isEqualTo(sampleUuid);
        assertThat(actualResult.matchedNumbers()).isEqualTo(sampleWinningNumbers);
        assertThat(actualResult.matchedNumbers().size()).isEqualTo(6);
    }

    @Test
    @DisplayName("should delete lottery results when uuid its is provided")
    void deleteLotteryResultsForUuid_givenUuid_shouldDeleteLotteryResult() {
        // given
        resultsCheckerFacade.generateLotteryResultsForDrawDate(sampleDrawDateTime);

        // when
        Optional<LotteryResultsDto> resultDtoOptional = resultsCheckerFacade.deleteLotteryResultsForUuid(sampleUuid);
        LotteryResultsDto actualResult = resultDtoOptional.orElse(null);

        // then
        assertThat(actualResult).isNotNull();
        assertThat(actualResult.uuid()).isEqualTo(sampleUuid);
        assertThat(resultsCheckerFacade.getResultsForId(sampleUuid)).isEmpty();
    }

    @Test
    @DisplayName("should return list of lottery results when draw date is provided")
    void getLotteryResultsForDrawDate_givenDrawDate_shouldReturnListOfLotteryResults() {
        // given
        resultsCheckerFacade.generateLotteryResultsForDrawDate(sampleDrawDateTime);

        // when
        List<LotteryResultsDto> lotteryResults = resultsCheckerFacade.getLotteryResultsForDrawDate(sampleDrawDateTime);

        // then
        int expectedSize = 6;
        assertThat(lotteryResults).hasSize(expectedSize);
        assertThat(lotteryResults).map(LotteryResultsDto::uuid).contains(sampleUuid);
    }

    @Test
    @DisplayName("should return lottery results only for winners")
    void getLotteryResultsDrawDateWinnersOnly_givenDrawDate_returnsOnlyWinners() {
        // given
        resultsCheckerFacade.generateLotteryResultsForDrawDate(sampleDrawDateTime);

        // when
        List<LotteryResultsDto> lotteryResultsWinners = resultsCheckerFacade.getLotteryResultsDrawDateWinnersOnly(sampleDrawDateTime);

        // then
        int expectedSize = 3;
        assertThat(lotteryResultsWinners).hasSize(expectedSize);
        assertThat(lotteryResultsWinners).map(LotteryResultsDto::uuid).contains(sampleUuid);
    }

    @Test
    @DisplayName("should delete all lottery results dor that draw date when draw date is provided")
    void deleteLotteryResultsForDrawDate_givenUuid_shouldDeleteAllLotteryResultsForThatDrawDate() {
        // given
        resultsCheckerFacade.generateLotteryResultsForDrawDate(sampleDrawDateTime);

        // when
        List<LotteryResultsDto> deletedLotteryResults = resultsCheckerFacade.deleteLotteryResultsForDrawDate(sampleDrawDateTime);

        // then
        int expectedDeletedListSize = 6;
        assertThat(deletedLotteryResults).hasSize(expectedDeletedListSize);
        int expectedLotteryResultsListSize = 0;
        List<LotteryResultsDto> resultsLeftInRepo = resultsCheckerFacade.deleteLotteryResultsForDrawDate(sampleDrawDateTime);
        assertThat(resultsLeftInRepo).hasSize(expectedLotteryResultsListSize);
    }

    @Test
    @DisplayName("should return all lottery results")
    void getAllLotteryResults_givenDrawDate_returnsAllLotteryResults() {
        // given
        resultsCheckerFacade.generateLotteryResultsForDrawDate(sampleDrawDateTime);

        // when
        List<LotteryResultsDto> allLotteryResults = resultsCheckerFacade.getAllLotteryResults();

        // then
        int expectedDeletedListSize = 6;
        assertThat(allLotteryResults).hasSize(expectedDeletedListSize);
    }

}
