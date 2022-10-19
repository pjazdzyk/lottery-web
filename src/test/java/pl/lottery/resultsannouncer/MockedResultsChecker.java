package pl.lottery.resultsannouncer;

import pl.lottery.resultschecker.ResultsCheckerFacade;
import pl.lottery.resultschecker.dto.CheckerDto;
import pl.lottery.resultschecker.dto.CheckerStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

interface MockedResultsChecker {

    LocalDateTime sampleDrawDateTime = LocalDateTime.of(2021, 8, 12, 20, 0);
    UUID sampleUuid = UUID.fromString("ef241277-64a2-457a-beea-a4c589803a26");
    List<Integer> sampleWinningNumbers = List.of(1, 2, 3, 4, 5, 6);
    CheckerStatus publishedStatus = CheckerStatus.OK;
    UUID nonExistingUUid = UUID.fromString("cb245bd4-bc47-40ed-87dc-caa9720186bd");
    CheckerDto notFoundDto = new CheckerDto(null, null, null, null, null, false, CheckerStatus.NOT_FOUND);

    List<CheckerDto> sampleCheckerDtos = List.of(
            // Cached
            new CheckerDto(sampleUuid, sampleDrawDateTime, List.of(1, 2, 3, 4, 5, 6), sampleWinningNumbers, List.of(1, 2, 3, 4, 5, 6), true, publishedStatus),
            new CheckerDto(UUID.randomUUID(), sampleDrawDateTime, List.of(1, 2, 3, 4, 50, 60), sampleWinningNumbers, List.of(1, 2, 3, 4), true, publishedStatus),
            new CheckerDto(UUID.randomUUID(), sampleDrawDateTime, List.of(1, 2, 3, 70, 80, 90), sampleWinningNumbers, List.of(1, 2, 3), true, publishedStatus)
    );

    default ResultsCheckerFacade createMockedResultsCheckerFacade() {
        ResultsCheckerFacade mockedResultsCheckerFacade = mock(ResultsCheckerFacade.class);
        when(mockedResultsCheckerFacade.getResultsForId(sampleUuid)).thenReturn(sampleCheckerDtos.get(0));
        when(mockedResultsCheckerFacade.getResultsForId(nonExistingUUid)).thenReturn(notFoundDto);
        when(mockedResultsCheckerFacade.getResultsForId(isNull())).thenReturn(notFoundDto);
        return mockedResultsCheckerFacade;
    }

}