package pl.lotto.infrastructure.controllers.restapi;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.lotto.resultsannouncer.ResultsAnnouncerFacade;
import pl.lotto.resultsannouncer.dto.AnnouncerRequestDto;
import pl.lotto.resultsannouncer.dto.AnnouncerResponseDto;

@RestController
public class ResultsAnnouncerRestController {

    private final ResultsAnnouncerFacade resultsAnnouncerFacade;

    public ResultsAnnouncerRestController(ResultsAnnouncerFacade resultsAnnouncerFacade) {
        this.resultsAnnouncerFacade = resultsAnnouncerFacade;
    }

    @PostMapping("/api/v1/results")
    public AnnouncerResponseDto getResultsForUuid(AnnouncerRequestDto announcerRequestDto){
        return resultsAnnouncerFacade.getResultsForId(announcerRequestDto.getRequestUuid());
    }

}
