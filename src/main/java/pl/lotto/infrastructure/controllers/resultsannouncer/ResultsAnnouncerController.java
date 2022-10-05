package pl.lotto.infrastructure.controllers.resultsannouncer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.lotto.resultsannouncer.ResultsAnnouncerFacade;
import pl.lotto.resultsannouncer.dto.PublishedResultsDto;

import java.util.UUID;

@RestController
class ResultsAnnouncerController {

    private final ResultsAnnouncerFacade resultsAnnouncerFacade;

    public ResultsAnnouncerController(ResultsAnnouncerFacade resultsAnnouncerFacade) {
        this.resultsAnnouncerFacade = resultsAnnouncerFacade;
    }

    @GetMapping("/results")
    PublishedResultsDto getResultsForUuid(@RequestParam(value = "formUuid") String couponUuid){
        UUID uuid = UUID.fromString(couponUuid);
        return resultsAnnouncerFacade.getResultsForId(uuid);
    }

}
