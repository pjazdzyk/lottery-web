package pl.lotto.infrastructure.controllers.webapi;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.lotto.resultsannouncer.ResultsAnnouncerFacade;
import pl.lotto.resultsannouncer.dto.AnnouncerRequestDto;
import pl.lotto.resultsannouncer.dto.AnnouncerResponseDto;

@Controller
public class ResultsAnnouncerWebController {

    private final ResultsAnnouncerFacade resultsAnnouncerFacade;

    public ResultsAnnouncerWebController(ResultsAnnouncerFacade resultsAnnouncerFacade) {
        this.resultsAnnouncerFacade = resultsAnnouncerFacade;
    }

    @PostMapping("/results")
    @ResponseBody
    public AnnouncerResponseDto getResultsForUuid(AnnouncerRequestDto announcerRequestDto){
        return resultsAnnouncerFacade.getResultsForId(announcerRequestDto.getRequestUuid());
    }

}
