package pl.lotto.infrastructure.controllers.resultsannouncer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.lotto.resultsannouncer.ResultsAnnouncerFacade;
import pl.lotto.resultsannouncer.dto.AnnouncerRequestDto;
import pl.lotto.resultsannouncer.dto.AnnouncerResponseDto;

@RestController
class ResultsAnnouncerController {

    private final ResultsAnnouncerFacade resultsAnnouncerFacade;

    public ResultsAnnouncerController(ResultsAnnouncerFacade resultsAnnouncerFacade) {
        this.resultsAnnouncerFacade = resultsAnnouncerFacade;
    }

    @GetMapping("/results")
    AnnouncerResponseDto getResultsForUuid(AnnouncerRequestDto announcerRequestDto){
        return resultsAnnouncerFacade.getResultsForId(announcerRequestDto.getRequestUuid());
    }

}
