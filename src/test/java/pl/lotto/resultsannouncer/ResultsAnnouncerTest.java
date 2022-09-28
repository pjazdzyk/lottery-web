package pl.lotto.resultsannouncer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.lotto.resultsannouncer.dto.AnnouncerStatus;
import pl.lotto.resultsannouncer.dto.PublishedResultsDto;
import pl.lotto.resultschecker.ResultsCheckerFacade;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ResultsAnnouncerTest implements MockedResultsChecker{

    ResultsCheckerFacade mockedResultsCheckerFacade = createMockedResultsCheckerFacade();
    PublishedResultsCache publishedResultsCache = spy(new CacheRepositoryStub());
    ResultsAnnouncerConfiguration resultsAnnouncerConfig = new ResultsAnnouncerConfiguration();
    ResultsAnnouncerFacade resultsAnnouncerFacade = resultsAnnouncerConfig.createForTests(mockedResultsCheckerFacade,publishedResultsCache);

    @AfterEach
    void tearDown(){
        publishedResultsCache = new CacheRepositoryStub();
    }

    @Test
    @DisplayName("should return announcer dto, when uuid is provided")
    void getResultsForId_givenUuid_returnsAnnouncerDto() {
        // given
        // when
        PublishedResultsDto actualAnnouncerDto = resultsAnnouncerFacade.getResultsForId(sampleUuid);

        // then
        verify(publishedResultsCache, times(1)).save(any());
        assertThat(actualAnnouncerDto.uuid()).isEqualTo(sampleUuid);
        assertThat(actualAnnouncerDto.status()).isEqualTo(AnnouncerStatus.PUBLISHED);
    }

    @Test
    @DisplayName("should return save announcer dto only once when two or more request are made from the same uuid")
    void getResultsForId_givenUuidSecondTime_returnsAnnouncerDtoFromCache(){
        // given
        // when
        PublishedResultsDto firstQueryResponseDto = resultsAnnouncerFacade.getResultsForId(sampleUuid);
        PublishedResultsDto secondQueryResponseDto = resultsAnnouncerFacade.getResultsForId(sampleUuid);

        // then
        int expectedSaveInvocationCount = 1;
        verify(publishedResultsCache, times(expectedSaveInvocationCount)).save(any());
        AnnouncerStatus expectedFirstQueryStatus = AnnouncerStatus.PUBLISHED;
        assertThat(firstQueryResponseDto.status()).isEqualTo(expectedFirstQueryStatus);
        AnnouncerStatus expectedSecondQueryStatus = AnnouncerStatus.CACHED;
        assertThat(secondQueryResponseDto.status()).isEqualTo(expectedSecondQueryStatus);

    }

    @Test
    @DisplayName("should return not found dto if requested uuid is not in the database")
    void getResultsForId_givenInvalidUuid_returnsNotFoundDto() {
        // given
        // when
        PublishedResultsDto actualAnnouncerDto = resultsAnnouncerFacade.getResultsForId(nonExistingUUid);

        // then
        verify(publishedResultsCache, times(0)).save(any());
        assertThat(actualAnnouncerDto.status()).isEqualTo(AnnouncerStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("should return not found dto if null is passed instead of uuid")
    void getResultsForId_givenNullAsUUid_returnsNotFoundDto() {
        // given
        // when
        PublishedResultsDto actualAnnouncerDto = resultsAnnouncerFacade.getResultsForId(null);

        // then
        verify(publishedResultsCache, times(0)).save(any());
        assertThat(actualAnnouncerDto.status()).isEqualTo(AnnouncerStatus.NOT_FOUND);
    }


}



