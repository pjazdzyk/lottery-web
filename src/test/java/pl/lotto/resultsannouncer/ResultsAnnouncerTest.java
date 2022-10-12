package pl.lotto.resultsannouncer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.lotto.resultsannouncer.dto.AnnouncerStatus;
import pl.lotto.resultsannouncer.dto.AnnouncerResponseDto;
import pl.lotto.resultschecker.ResultsCheckerFacade;

import static org.assertj.core.api.Assertions.assertThat;

class ResultsAnnouncerTest implements MockedResultsChecker{

    ResultsCheckerFacade mockedResultsCheckerFacade = createMockedResultsCheckerFacade();
    ResultsAnnouncerConfiguration resultsAnnouncerConfig = new ResultsAnnouncerConfiguration();
    ResultsAnnouncerFacade resultsAnnouncerFacade = resultsAnnouncerConfig.createForTests(mockedResultsCheckerFacade);

    @Test
    @DisplayName("should return announcer dto, when uuid is provided")
    void getResultsForId_givenUuid_returnsAnnouncerDto() {
        // given
        // when
        AnnouncerResponseDto actualAnnouncerDto = resultsAnnouncerFacade.getResultsForId(sampleUuid);

        // then
        assertThat(actualAnnouncerDto.uuid()).isEqualTo(sampleUuid);
        assertThat(actualAnnouncerDto.status()).isEqualTo(AnnouncerStatus.PUBLISHED);
    }

     @Test
    @DisplayName("should return not found dto if requested uuid is not in the database")
    void getResultsForId_givenInvalidUuid_returnsNotFoundDto() {
        // given
        // when
        AnnouncerResponseDto actualAnnouncerDto = resultsAnnouncerFacade.getResultsForId(nonExistingUUid);

        // then
        assertThat(actualAnnouncerDto.status()).isEqualTo(AnnouncerStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("should return not found dto if null is passed instead of uuid")
    void getResultsForId_givenNullAsUUid_returnsNotFoundDto() {
        // given
        // when
        AnnouncerResponseDto actualAnnouncerDto = resultsAnnouncerFacade.getResultsForId(null);

        // then
        assertThat(actualAnnouncerDto.status()).isEqualTo(AnnouncerStatus.NOT_FOUND);
    }


}



