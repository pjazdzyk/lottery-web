package pl.lotto.resultsannouncer;

import pl.lotto.resultschecker.ResultsCheckerFacade;
import pl.lotto.resultschecker.dto.CheckerDto;
import pl.lotto.resultschecker.dto.CheckerStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

interface MockedResultsChecker {

    LocalDateTime sampleDrawDateTime = LocalDateTime.of(2021, 8, 12, 20, 0);
    UUID sampleUuid = UUID.fromString("ef241277-64a2-457a-beea-a4c589803a26");
    List<Integer> sampleWinningNumbers = List.of(1, 2, 3, 4, 5, 6);
    CheckerStatus publishedStatus = CheckerStatus.OK;

    List<CheckerDto> sampleCheckerDtos = List.of(
            // Cached
            new CheckerDto(sampleUuid, sampleDrawDateTime, List.of(1, 2, 3, 4, 5, 6), sampleWinningNumbers, List.of(1, 2, 3, 4, 5, 6), true, publishedStatus),
            new CheckerDto(UUID.randomUUID(), sampleDrawDateTime, List.of(1, 2, 3, 4, 50, 60), sampleWinningNumbers, List.of(1, 2, 3, 4), true, publishedStatus),
            new CheckerDto(UUID.randomUUID(), sampleDrawDateTime, List.of(1, 2, 3, 70, 80, 90), sampleWinningNumbers, List.of(1, 2, 3), true, publishedStatus)
    );

    default ResultsCheckerFacade createMockedResultsCheckerFacade() {
        ResultsCheckerFacade mockedResultsCheckerFacade = mock(ResultsCheckerFacade.class);
        when(mockedResultsCheckerFacade.getResultsForId(any(UUID.class))).thenReturn(sampleCheckerDtos.get(0));
        return mockedResultsCheckerFacade;
    }

}