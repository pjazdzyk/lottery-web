package pl.lotto.feat;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.lotto.BaseIntegrationSpec;
import pl.lotto.numberreceiver.dto.ReceiverResponseDto;
import pl.lotto.resultsannouncer.dto.AnnouncerResponseDto;
import pl.lotto.resultschecker.ResultsCheckerFacade;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;

class HappyPathTest extends BaseIntegrationSpec {

    private final List<Integer> winnersTypesNumbers = List.of(1, 2, 3, 4, 5, 6);
    private final List<Integer> losersTypesNumbers = List.of(20, 30, 40, 50, 60, 70);
    private final LocalDateTime DRAW_DATE = LocalDateTime.of(2022, 8, 13, 12, 0);

    @Autowired
    ResultsCheckerFacade resultsCheckerFacade;

    @Test
    @DisplayName("should user win when he inputs 6 winning numbers and retrieves results after the draw date")
    public void happyPath_shouldUserInputNumbers_andWin() {
        // given (date: 2022.08.08 - Monday)
        ReceiverResponseDto winnerReceiverDto = sendOneCouponToReceiverApi(winnersTypesNumbers);
        sendSomeCouponsToReceiverApi(10, losersTypesNumbers);
        // advancing to 5 seconds before draw date. (date: 2022.08.13 - Saturday)
        adjustableClock.setClockToLocalDate(LocalDate.of(2022, 8, 13));
        adjustableClock.setClockToLocalTime(LocalTime.of(15, 14, 55));

        // when
        Awaitility.setDefaultPollInterval(Duration.ofSeconds(2));
        await().atMost(65, TimeUnit.SECONDS)
                .until(checkIfResultsCheckerRepositoryContainsWinners(1));

        // then
        UUID expectedWinnerUuid = winnerReceiverDto.uuid();
        AnnouncerResponseDto announcerResponseDto = retrieveResultsFromAnnouncerApiFromUuid(expectedWinnerUuid);
    }

    private Callable<Boolean> checkIfResultsCheckerRepositoryContainsWinners(int winnersCount) {
        return () -> resultsCheckerFacade.getLotteryResultsDrawDateWinnersOnly(DRAW_DATE).size() == winnersCount;
    }

}
