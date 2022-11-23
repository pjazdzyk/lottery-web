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
    CheckerStatus publishedStatus = CheckerStatus.PROCESSED;
    UUID nonExistingUUid = UUID.fromString("cb245bd4-bc47-40ed-87dc-caa9720186bd");

    List<CheckerDto> sampleCheckerDtos = List.of(
            // Cached
            CheckerDto.CheckerDtoBuilder.newInstance(publishedStatus)
                    .withUid(sampleUuid)
                    .withDrawDate(sampleDrawDateTime)
                    .withTypedNumbers(List.of(1, 2, 3, 4, 5, 6))
                    .withWinningNumbers(sampleWinningNumbers)
                    .withMatchedNumbers(sampleWinningNumbers)
                    .withIsWinner(true)
                    .build(),

            CheckerDto.CheckerDtoBuilder.newInstance(publishedStatus)
                    .withUid(sampleUuid)
                    .withDrawDate(sampleDrawDateTime)
                    .withTypedNumbers(List.of(1, 2, 3, 4, 50, 60))
                    .withWinningNumbers(sampleWinningNumbers)
                    .withMatchedNumbers(List.of(1, 2, 3, 4))
                    .withIsWinner(true)
                    .build(),

            CheckerDto.CheckerDtoBuilder.newInstance(publishedStatus)
                    .withUid(sampleUuid)
                    .withDrawDate(sampleDrawDateTime)
                    .withTypedNumbers(List.of(1, 2, 3, 70, 80, 90))
                    .withWinningNumbers(sampleWinningNumbers)
                    .withMatchedNumbers(List.of(1, 2, 3))
                    .withIsWinner(true)
                    .build()
    );

    default ResultsCheckerFacade createMockedResultsCheckerFacade() {
        ResultsCheckerFacade mockedResultsCheckerFacade = mock(ResultsCheckerFacade.class);
        when(mockedResultsCheckerFacade.findResultsForId(sampleUuid)).thenReturn(sampleCheckerDtos.get(0));
        when(mockedResultsCheckerFacade.findResultsForId(nonExistingUUid)).thenReturn(CheckerDto.ofNotFoundDtoForUuid(nonExistingUUid));
        when(mockedResultsCheckerFacade.findResultsForId(isNull())).thenReturn(CheckerDto.ofNotFoundDtoForUuid(nonExistingUUid));
        return mockedResultsCheckerFacade;
    }

}