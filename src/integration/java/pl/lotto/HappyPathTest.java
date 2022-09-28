package pl.lotto;

import org.junit.jupiter.api.Test;
import pl.lotto.numberreceiver.dto.ReceiverDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class HappyPathTest extends BaseIntegrationSpec {

    //TODO - in progress
    @Test
    void shouldReturnLotteryResults_whenInputNumbersAreProvided() {
        // given
        List<ReceiverDto> userInputs = seedFiveRandomUserInputs();
        UUID trackedUuid = userInputs.get(0).uuid();
        adjustableClock.plusDays(3);
        seedFiveRandomUserInputs();

        // when
        LocalDateTime drawDate = timeGeneratorFacade.getDrawDateAndTime();
        boolean lotteryWasRun = winningNumberGeneratorFacade.runLottery();
        List<ReceiverDto> actualCouponList = numberReceiverFacade.getAllCoupons();

        // then
        int expectedSize = 10;
        assertThat(lotteryWasRun).isTrue();
        assertThat(actualCouponList).hasSize(expectedSize);

        // when
        adjustableClock.plusDays(3);
        int actualGeneratedLotteryResults = resultsCheckerFacade.generateLotteryResultsForDrawDate(drawDate);

        // then
        int expectedLotteryResults = 10;
        assertThat(actualGeneratedLotteryResults).isEqualTo(expectedLotteryResults);

    }

}
