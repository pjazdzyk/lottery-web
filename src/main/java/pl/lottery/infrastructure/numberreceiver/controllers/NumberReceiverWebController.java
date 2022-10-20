package pl.lottery.infrastructure.numberreceiver.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import pl.lottery.numberreceiver.NumberReceiverFacade;
import pl.lottery.numberreceiver.dto.ReceiverRequestDto;
import pl.lottery.numberreceiver.dto.ReceiverResponseDto;

@Controller
public class NumberReceiverWebController {

    private final NumberReceiverFacade numberReceiverFacade;

    public NumberReceiverWebController(NumberReceiverFacade numberReceiverFacade) {
        this.numberReceiverFacade = numberReceiverFacade;
    }

    @PostMapping("/receiver")
    public String inputNumbers(ReceiverRequestDto receiverRequestDto, Model model) {
        ReceiverResponseDto receiverResponseDto = numberReceiverFacade.inputNumbers(receiverRequestDto.getTypedNumbers());
        model.addAttribute("receiverResponseDto", receiverResponseDto);
        return "receiver-resp-view";
    }

}
