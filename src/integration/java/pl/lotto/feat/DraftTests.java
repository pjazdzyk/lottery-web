package pl.lotto.feat;

import org.junit.jupiter.api.Test;
import pl.lotto.BaseIntegrationSpec;
import pl.lotto.numberreceiver.dto.ReceiverResponseDto;
import pl.lotto.resultsannouncer.dto.AnnouncerStatus;
import pl.lotto.resultsannouncer.dto.AnnouncerResponseDto;
import pl.lotto.resultschecker.dto.CheckerDto;
import pl.lotto.winningnumbergenerator.dto.WinNumberStatus;
import pl.lotto.winningnumbergenerator.dto.WinningNumbersDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


class DraftTests extends BaseIntegrationSpec {


    //TODO - in progress
    @Test
    void draftFacadeTests() {
        // initial date: (2022.08.08 - monday)
        List<ReceiverResponseDto> userInputs = seedFiveRandomUserInputs();
        UUID trackedUuid = userInputs.get(0).uuid();
        // going to date: (2022.08.11 - thursday)
        adjustableClock.plusDays(3);
        seedFiveRandomUserInputs();
        LocalDateTime expectedDrawDate = timeGeneratorFacade.getDrawDateAndTime();

        // getting to the draw day (2022.08.13 - Saturday)
        //TODO advance in time somehow

        // winning numbers should be automatically generated
        // results checker should automatically process the results

        // when
        AnnouncerResponseDto resultsForId = resultsAnnouncerFacade.getResultsForId(trackedUuid);
        WinningNumbersDto winningNumbersForDate = winningNumberGeneratorFacade.getWinningNumbersForDate(expectedDrawDate);
        List<CheckerDto> lotteryResultsForDrawDate = resultsCheckerFacade.getLotteryResultsForDrawDate(expectedDrawDate);

        // then
        assertThat(winningNumbersForDate.status()).isNotEqualTo(WinNumberStatus.NOT_FOUND);
        assertThat(lotteryResultsForDrawDate).isNotEmpty();
        assertThat(resultsForId.status()).isNotEqualTo(AnnouncerStatus.NOT_FOUND);

    }

}
