package pl.lottery.infrastructure.resultsannouncer.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.lottery.resultsannouncer.ResultsAnnouncerFacade;
import pl.lottery.resultsannouncer.dto.AnnouncerResponseDto;

import java.util.UUID;

@RestController
public class ResultsAnnouncerRestController {

    private final ResultsAnnouncerFacade resultsAnnouncerFacade;

    public ResultsAnnouncerRestController(ResultsAnnouncerFacade resultsAnnouncerFacade) {
        this.resultsAnnouncerFacade = resultsAnnouncerFacade;
    }

    @GetMapping(value = "/api/v1/results")
    public ResponseEntity<AnnouncerResponseDto> getResultsForUuid(@RequestParam String requestUuid) {
        UUID userRequestedUuid = UUID.fromString(requestUuid);
        AnnouncerResponseDto resultsForId = resultsAnnouncerFacade.getResultsForId(userRequestedUuid);
        return new ResponseEntity<>(resultsForId, HttpStatus.OK);
    }

}
