package pl.lottery.features;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.lottery.BaseIntegrationSpec;
import pl.lottery.DefaultJsonBodies;
import pl.lottery.numberreceiver.dto.ReceiverResponseDto;
import pl.lottery.resultsannouncer.dto.AnnouncerResponseDto;
import pl.lottery.resultsannouncer.dto.AnnouncerStatus;

import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

class LotteryGameIsSuccessfulIT extends BaseIntegrationSpec {

    @Test
    @DisplayName("should user win when he inputs 6 winning numbers and retrieves results after the draw date")
    public void happyPath_shouldUserInputNumbers_andWin() {
        // given (date: 2022.08.08 - Monday)
        int winnersCount = 1;
        ReceiverResponseDto winnerReceiverDto = mockMvcReceiverCaller.makePostCallReceiverInputNumbers(WINNERS_TYPED_NUMBERS);
        UUID winnerUuid = winnerReceiverDto.uuid();
        mockMvcReceiverCaller.sendSomeCouponsToReceiverApi(5, LOSERS_TYPED_NUMBERS);
        stubPostCallToGenerateEndpoint(DefaultJsonBodies.winningNumsDefaultRequest, DefaultJsonBodies.winningNumsDefaultGenerateResponse);
        stubGetCallToRetrieveEndpointWithParam(DefaultJsonBodies.winningNumsDefaultRetrieveResponse, DRAW_DATE.toString());

        // when
        await().atMost(10, TimeUnit.SECONDS)
               .until(checkIfResultsCheckerRepositoryContainsWinners(winnersCount));
        AnnouncerResponseDto announcerResponseDto = mockMvcAnnouncerCaller.makeGetCallToRetrieveResultsByUuid(winnerUuid);

        // then
        AnnouncerStatus expectedStatus = AnnouncerStatus.PUBLISHED;
        assertThat(announcerResponseDto.uuid()).isEqualTo(winnerUuid);
        assertThat(announcerResponseDto.drawDate()).isEqualTo(DRAW_DATE);
        assertThat(announcerResponseDto.status()).isEqualTo(expectedStatus);
        assertThat(announcerResponseDto.typedNumbers()).isEqualTo(WINNERS_TYPED_NUMBERS);
    }

    private Callable<Boolean> checkIfResultsCheckerRepositoryContainsWinners(int winnersCount) {
        return () -> resultsCheckerFacade.findLotteryResultsDrawDateWinnersOnly(DRAW_DATE).size() == winnersCount;
    }

}
