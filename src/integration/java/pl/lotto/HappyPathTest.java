package pl.lotto;

import org.junit.jupiter.api.Test;
import pl.lotto.numberreceiver.dto.ReceiverDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

class HappyPathTest extends BaseIntegrationSpec {

    @Test
    void shouldReturnLotteryResults_whenInputNumbersAreProvided() {
        // given
        List<ReceiverDto> userInputs = seedFiveRandomUserInputs();
        UUID trackedUuid = userInputs.get(0).uuid();
        adjustableClock.plusDays(3);
        seedFiveRandomUserInputs();

        // when
        LocalDateTime drawDate = timeGeneratorFacade.getDrawDateAndTime();
        winningNumberGeneratorFacade.runLottery();
        resultsCheckerFacade.generateLotteryResultsForDrawDate(drawDate);
        adjustableClock.plusDays(7);
        LocalDateTime drawDate1 = timeGeneratorFacade.getDrawDateAndTime();
        winningNumberGeneratorFacade.runLottery();
        resultsCheckerFacade.generateLotteryResultsForDrawDate(drawDate1);

    }


}
