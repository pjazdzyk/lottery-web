package pl.lotto.resultschecker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.lotto.numberreceiver.NumberReceiverFacade;
import pl.lotto.resultschecker.dto.CheckerStatus;
import pl.lotto.resultschecker.dto.CheckerDto;
import pl.lotto.infrastructure.winningnumberservice.WinningNumberGenerable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ResultCheckerFacadeTest implements MockedNumberReceiverFacade {

    private final List<Integer> winningNumbers = List.of(1, 2, 3, 4, 5, 6);
    private final NumberReceiverFacade mockedNumberReceiverFacade = createMockedNumberReceiverFacade();
    private final WinningNumberGenerable winningNumbersServiceStub = new WinningNumbersServiceStub(winningNumbers);
    private final ResultsCheckerConfiguration resultsCheckerConfig = new ResultsCheckerConfiguration();
    private ResultsCheckerRepository resultsCheckerRepository = new ResultsCheckerRepositoryInMemory();
    private final int minNumbersToWin = 3;
    private final CheckerConfigurable winningConfig = new CheckerPropertyConfigTest(minNumbersToWin);
    private final ResultsCheckerFacade resultsCheckerFacade = resultsCheckerConfig.createForTests(mockedNumberReceiverFacade,
            winningNumbersServiceStub, resultsCheckerRepository, winningConfig);

    @BeforeEach
    void tearDown() {
        resultsCheckerRepository = new ResultsCheckerRepositoryInMemory();
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
        CheckerDto actualResultCheckDto = resultsCheckerFacade.getResultsForId(sampleUuid);

        // then
        assertThat(actualResultCheckDto.uuid()).isEqualTo(sampleUuid);
        assertThat(actualResultCheckDto.status()).isEqualTo(CheckerStatus.OK);
        assertThat(actualResultCheckDto.matchedNumbers()).isEqualTo(winningNumbers);
        assertThat(actualResultCheckDto.matchedNumbers().size()).isEqualTo(6);
    }

    @Test
    @DisplayName("should return not found lottery result when provided coupon uuid which is not in the database")
    void getLotteryResultForUuid_givenInvalidUuid_returnLotteryNotFoundDto() {
        // given
        resultsCheckerFacade.generateLotteryResultsForDrawDate(sampleDrawDateTime);
        UUID invalidUUid = UUID.fromString("7bc27e59-52f4-4bd8-974b-c3a62e8c4f92");

        // when
        CheckerDto actualResultCheckDto = resultsCheckerFacade.getResultsForId(invalidUUid);

        // then
        assertThat(actualResultCheckDto).isNotNull();
        assertThat(actualResultCheckDto.status()).isEqualTo(CheckerStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("should delete lottery results when uuid is provided")
    void deleteLotteryResultsForUuid_givenUuid_shouldDeleteLotteryResult() {
        // given
        resultsCheckerFacade.generateLotteryResultsForDrawDate(sampleDrawDateTime);

        // when
        CheckerDto deletedCheckerResultDTo = resultsCheckerFacade.deleteLotteryResultsForUuid(sampleUuid);

        // then
        assertThat(deletedCheckerResultDTo.uuid()).isEqualTo(sampleUuid);
        assertThat(deletedCheckerResultDTo.status()).isEqualTo(CheckerStatus.DELETED);
        assertThat(resultsCheckerFacade.getResultsForId(sampleUuid).status()).isEqualTo(CheckerStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("should return list of lottery results when draw date is provided")
    void getLotteryResultsForDrawDate_givenDrawDate_shouldReturnListOfLotteryResults() {
        // given
        resultsCheckerFacade.generateLotteryResultsForDrawDate(sampleDrawDateTime);

        // when
        List<CheckerDto> lotteryResults = resultsCheckerFacade.getLotteryResultsForDrawDate(sampleDrawDateTime);

        // then
        int expectedSize = 6;
        assertThat(lotteryResults).hasSize(expectedSize);
        assertThat(lotteryResults).map(CheckerDto::uuid).contains(sampleUuid);
    }

    @Test
    @DisplayName("should return empty list when provided draw date does not exist in repository")
    void getLotteryResultsForDrawDate_givenInvalidDrawDate_shouldReturnEmptyList() {
        // given
        resultsCheckerFacade.generateLotteryResultsForDrawDate(sampleDrawDateTime);

        // when
        List<CheckerDto> lotteryResults = resultsCheckerFacade.getLotteryResultsForDrawDate(LocalDateTime.of(2050, 1, 1, 1, 1, 1));

        // then
        assertThat(lotteryResults).isNotNull();
        assertThat(lotteryResults).isEmpty();
    }

    @Test
    @DisplayName("should return lottery results only for winners")
    void getLotteryResultsDrawDateWinnersOnly_givenDrawDate_returnsOnlyWinners() {
        // given
        resultsCheckerFacade.generateLotteryResultsForDrawDate(sampleDrawDateTime);

        // when
        List<CheckerDto> lotteryResultsWinners = resultsCheckerFacade.getLotteryResultsDrawDateWinnersOnly(sampleDrawDateTime);

        // then
        int expectedSize = 3;
        assertThat(lotteryResultsWinners).hasSize(expectedSize);
        assertThat(lotteryResultsWinners).allMatch(CheckerDto::isWinner);
        assertThat(lotteryResultsWinners).allMatch(dto -> dto.status() == CheckerStatus.OK);
        assertThat(lotteryResultsWinners).map(CheckerDto::uuid).contains(sampleUuid);
    }

    @Test
    @DisplayName("should return all lottery results")
    void getAllLotteryResults_givenDrawDate_returnsAllLotteryResults() {
        // given
        resultsCheckerFacade.generateLotteryResultsForDrawDate(sampleDrawDateTime);

        // when
        List<CheckerDto> actualAllLotteryResults = resultsCheckerFacade.getAllLotteryResults();

        // then
        int expectedDeletedListSize = 6;
        assertThat(actualAllLotteryResults).hasSize(expectedDeletedListSize);
    }

}
