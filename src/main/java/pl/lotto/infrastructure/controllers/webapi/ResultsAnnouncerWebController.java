package pl.lotto.infrastructure.controllers.webapi;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import pl.lotto.resultsannouncer.ResultsAnnouncerFacade;
import pl.lotto.resultsannouncer.dto.AnnouncerRequestDto;

@Controller
public class ResultsAnnouncerWebController {

    private final ResultsAnnouncerFacade resultsAnnouncerFacade;

    public ResultsAnnouncerWebController(ResultsAnnouncerFacade resultsAnnouncerFacade) {
        this.resultsAnnouncerFacade = resultsAnnouncerFacade;
    }

    @PostMapping("/results")
    public String getResultsForUuid(AnnouncerRequestDto announcerRequestDto) {
        resultsAnnouncerFacade.getResultsForId(announcerRequestDto.getRequestUuid());
        return "announcer-resp-view";
    }

}
