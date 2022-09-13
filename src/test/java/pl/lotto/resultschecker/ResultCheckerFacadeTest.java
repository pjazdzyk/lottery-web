package pl.lotto.resultschecker;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.lotto.numberreceiver.NumberReceiverFacade;
import pl.lotto.resultschecker.dto.LotteryResultsDto;
import pl.lotto.winningnumbergenerator.WinningNumberGeneratorFacade;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class ResultCheckerFacadeTest implements MockedNumberReceiverFacade, MockedWinningNumberGeneratorFacade {

    NumberReceiverFacade mockedNumberReceiverFacade = createMockedNumberReceiverFacade();
    WinningNumberGeneratorFacade mockedWinningNumbersFacade = createWinningNumberFacade();
    ResultsCheckerConfiguration resultsCheckerConfig = new ResultsCheckerConfiguration();
    ResultCheckerRepository resultCheckerRepository = new ResultsCheckerRepositoryStub();
    ResultsCheckerFacade resultsCheckerFacade = resultsCheckerConfig.createForTests(mockedNumberReceiverFacade, mockedWinningNumbersFacade, resultCheckerRepository);

    @Test
    @DisplayName("should return lottery result dto when coupon uuid is provided")
    void getLotteryResult_givenUuid_returnLotteryResultDto() {
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


}
