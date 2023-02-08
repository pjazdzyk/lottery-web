package pl.lotto.infrastructure.resultsannouncer.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping(value = "/api/v1/results")
    public ResponseEntity<AnnouncerResponseDto> getResultsForUuid(@RequestBody AnnouncerRequestDto announcerRequestDto) {
        AnnouncerResponseDto resultsForId = resultsAnnouncerFacade.getResultsForId(announcerRequestDto.getRequestUuid());
        return new ResponseEntity<>(resultsForId, HttpStatus.OK);
    }

}
