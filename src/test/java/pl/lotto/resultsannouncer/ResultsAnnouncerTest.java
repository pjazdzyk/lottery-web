package pl.lotto.resultsannouncer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.lotto.resultsannouncer.dto.AnnouncerStatus;
import pl.lotto.resultsannouncer.dto.PublishedResultsDto;
import pl.lotto.resultschecker.ResultsCheckerFacade;

import static org.assertj.core.api.Assertions.assertThat;

class ResultsAnnouncerTest implements MockedResultsChecker{

    ResultsCheckerFacade mockedResultsCheckerFacade = createMockedResultsCheckerFacade();
    PublishedResultsCache publishedResultsCache = new CacheRepositoryStub();
    ResultsAnnouncerConfiguration resultsAnnouncerConfig = new ResultsAnnouncerConfiguration();
    ResultsAnnouncerFacade resultsAnnouncerFacade = resultsAnnouncerConfig.createForTests(mockedResultsCheckerFacade,publishedResultsCache);

    @Test
    @DisplayName("should return announcer dto, when uuid is provided")
    void getResultsForId_givenUuid_returnsAnnouncerDto() {
        // given
        // when
        PublishedResultsDto actualAnnouncerDto = resultsAnnouncerFacade.getResultsForId(sampleUuid);

        // then
        assertThat(actualAnnouncerDto).isNotNull();
        assertThat(actualAnnouncerDto.uuid()).isEqualTo(sampleUuid);
        assertThat(actualAnnouncerDto.status()).isEqualTo(AnnouncerStatus.PUBLISHED);
    }

}



