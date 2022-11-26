package pl.lottery.infrastructure.controllers.resultsannouncer;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.lottery.resultsannouncer.ResultsAnnouncerFacade;
import pl.lottery.resultsannouncer.dto.AnnouncerResponseDto;
import pl.lottery.resultsannouncer.dto.AnnouncerStatus;

import java.util.UUID;

@Controller
public class ResultsAnnouncerWebController {

    public static String INVALID_UUID_MSG = "You have provided invalid coupon ID as: [%s]. Please check your coupon ID and paste it again.";
    public static String NOT_FOUND_MSG = "You have provided ID: [%s]. Your coupon has not been processed yet or it does not exist in lottery results archive.";
    private final ResultsAnnouncerFacade resultsAnnouncerFacade;

    public ResultsAnnouncerWebController(ResultsAnnouncerFacade resultsAnnouncerFacade) {
        this.resultsAnnouncerFacade = resultsAnnouncerFacade;
    }

    @GetMapping("/announcer")
    public String showResultsView(){
        return "announcer";
    }

    @GetMapping("/check-results")
    public String getResultsForUuid(@RequestParam String requestUuid, Model model) {
        if (resultsAnnouncerFacade.isInvalidUuid(requestUuid)) {
            model.addAttribute("errorMsg", String.format(INVALID_UUID_MSG, requestUuid));
            return "announcer-resp-error";
        }
        UUID userRequestedUuid = UUID.fromString(requestUuid);
        AnnouncerResponseDto announcerResponseDto = resultsAnnouncerFacade.findResultsForId(userRequestedUuid);
        if (announcerResponseDto.status() != AnnouncerStatus.PUBLISHED) {
            model.addAttribute("errorMsg", String.format(NOT_FOUND_MSG, requestUuid));
            return "announcer-resp-error";
        }
        model.addAttribute("announcerResponseDto", announcerResponseDto);
        return "announcer-resp-view";
    }

}
