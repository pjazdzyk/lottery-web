package pl.lottery.infrastructure.controllers.numberreceiver;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import pl.lottery.numberreceiver.NumberReceiverFacade;
import pl.lottery.numberreceiver.dto.ReceiverRequestDto;
import pl.lottery.numberreceiver.dto.ReceiverResponseDto;

import java.util.List;

@Controller
public class NumberReceiverWebController {

    private final NumberReceiverFacade numberReceiverFacade;

    public NumberReceiverWebController(NumberReceiverFacade numberReceiverFacade) {
        this.numberReceiverFacade = numberReceiverFacade;
    }

    @PostMapping("/receiver")
    public String inputNumbers(ReceiverRequestDto receiverRequestDto, Model model) {
        List<Integer> typedNumbers = receiverRequestDto.getTypedNumbers();
        if(numberReceiverFacade.numbersAreNotValid(typedNumbers)){
            model.addAttribute("typedNumbers", typedNumbers);
            return "receiver-resp-error";
        }
        ReceiverResponseDto receiverResponseDto = numberReceiverFacade.inputNumbers(typedNumbers);
        model.addAttribute("receiverResponseDto", receiverResponseDto);
        return "receiver-resp-view";
    }

}
