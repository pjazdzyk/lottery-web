package com.synerset.lottery.infrastructure.controllers.resultsannouncer;

import com.synerset.lottery.infrastructure.exceptions.resultsannouncer.ApiInvalidUuidRequestException;
import com.synerset.lottery.resultsannouncer.ResultsAnnouncerFacade;
import com.synerset.lottery.resultsannouncer.dto.AnnouncerResponseDto;
import com.synerset.lottery.resultsannouncer.dto.AnnouncerStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class ResultsAnnouncerRestController {

    private final ResultsAnnouncerFacade resultsAnnouncerFacade;

    public ResultsAnnouncerRestController(ResultsAnnouncerFacade resultsAnnouncerFacade) {
        this.resultsAnnouncerFacade = resultsAnnouncerFacade;
    }

    @GetMapping(value = AnnouncerEndpointVersions.API_VERSION_V1 + "/results/{id}")
    public ResponseEntity<AnnouncerResponseDto> getResultsForUuid(@PathVariable String id) {
        if(resultsAnnouncerFacade.isInvalidUuid(id)){
            throw new ApiInvalidUuidRequestException("Invalid Id. Please try again.");
        }
        UUID userRequestedUuid = UUID.fromString(id);
        AnnouncerResponseDto resultsForId = resultsAnnouncerFacade.findResultsForId(userRequestedUuid);
        HttpStatus httpStatus = resultsForId.status() == AnnouncerStatus.NOT_FOUND
                ? HttpStatus.NOT_FOUND
                : HttpStatus.OK;
        return new ResponseEntity<>(resultsForId, httpStatus);
    }
}
