package pl.lottery.feat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.lottery.BaseIntegrationSpec;
import pl.lottery.numberreceiver.dto.ReceiverResponseDto;
import pl.lottery.resultsannouncer.dto.AnnouncerResponseDto;
import pl.lottery.resultsannouncer.dto.AnnouncerStatus;
import pl.lottery.resultschecker.ResultsCheckerFacade;
import pl.lottery.mockmvccaller.MockMvcAnnouncerCaller;
import pl.lottery.mockmvccaller.MockMvcReceiverCaller;
import pl.lottery.TestConstants;

import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

class HappyPathTest extends BaseIntegrationSpec implements TestConstants {

    @Autowired
    MockMvcAnnouncerCaller mockMvcAnnouncerCaller;
    @Autowired
    MockMvcReceiverCaller mockMvcReceiverCaller;
    @Autowired
    ResultsCheckerFacade resultsCheckerFacade;

    @Test
    @DisplayName("should user win when he inputs 6 winning numbers and retrieves results after the draw date")
    public void happyPath_shouldUserInputNumbers_andWin() {
        // given (date: 2022.08.08 - Monday)
        ReceiverResponseDto winnerReceiverDto = mockMvcReceiverCaller.makePostCallReceiverInputNumbers(WINNERS_TYPED_NUMBERS);
        UUID winnerUuid = winnerReceiverDto.uuid();
        mockMvcReceiverCaller.sendSomeCouponsToReceiverApi(5, LOSERS_TYPED_NUMBERS);

        // when
        await().atMost(10, TimeUnit.SECONDS);
        AnnouncerResponseDto announcerResponseDto = mockMvcAnnouncerCaller.makeGetCallToRetrieveResultsByUuid(winnerUuid);

        // then
        AnnouncerStatus expectedStatus = AnnouncerStatus.PUBLISHED;
        assertThat(announcerResponseDto.uuid()).isEqualTo(winnerUuid);
        assertThat(announcerResponseDto.drawDate()).isEqualTo(DRAW_DATE);
        assertThat(announcerResponseDto.status()).isEqualTo(expectedStatus);
        assertThat(announcerResponseDto.typedNumbers()).isEqualTo(WINNERS_TYPED_NUMBERS);

    }

}
