package pl.lotto.infrastructure.timegenerator.propertyreadconfig.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import pl.lotto.numberreceiver.NumberReceiverFacade;
import pl.lotto.numberreceiver.dto.ReceiverRequestDto;
import pl.lotto.numberreceiver.dto.ReceiverResponseDto;

@Controller
public class NumberReceiverWebController {

    private final NumberReceiverFacade numberReceiverFacade;

    public NumberReceiverWebController(NumberReceiverFacade numberReceiverFacade) {
        this.numberReceiverFacade = numberReceiverFacade;
    }

    @PostMapping("/receiver")
    public String getInputNumbersFromUser(ReceiverRequestDto receiverRequestDto, Model model) {
        ReceiverResponseDto receiverResponseDto = numberReceiverFacade.inputNumbers(receiverRequestDto.getTypedNumbers());
        model.addAttribute("receiverResponseDto", receiverResponseDto);
        return "receiver-resp-view";
    }

}
