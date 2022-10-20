package pl.lottery.infrastructure.resultsannouncer.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.lottery.resultsannouncer.ResultsAnnouncerFacade;
import pl.lottery.resultsannouncer.dto.AnnouncerResponseDto;
import pl.lottery.resultsannouncer.dto.AnnouncerStatus;

import java.util.UUID;

@Controller
public class ResultsAnnouncerWebController {

    private final ResultsAnnouncerFacade resultsAnnouncerFacade;

    public ResultsAnnouncerWebController(ResultsAnnouncerFacade resultsAnnouncerFacade) {
        this.resultsAnnouncerFacade = resultsAnnouncerFacade;
    }

    @GetMapping("/results")
    public String getResultsForUuid(@RequestParam String requestUuid, Model model) {
        UUID userRequestedUuid = UUID.fromString(requestUuid);
        AnnouncerResponseDto announcerResponseDto = resultsAnnouncerFacade.getResultsForId(userRequestedUuid);
        model.addAttribute("announcerResponseDto", announcerResponseDto);
        if(announcerResponseDto.status()!= AnnouncerStatus.PUBLISHED){
            return "announcer-resp-error";
        }
        return "announcer-resp-view";
    }

}
