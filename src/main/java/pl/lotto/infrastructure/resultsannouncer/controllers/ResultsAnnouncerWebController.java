package pl.lotto.infrastructure.resultsannouncer.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/results")
    public String getResultsForUuid(AnnouncerRequestDto announcerRequestDto, Model model) {
        AnnouncerResponseDto announcerResponseDto = resultsAnnouncerFacade.getResultsForId(announcerRequestDto.getRequestUuid());
        model.addAttribute("announcerResponseDto",announcerResponseDto);
        return "announcer-resp-view";
    }

}
