package pl.lotto.infrastructure.controllers.webapi;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
    @ResponseBody
    public ReceiverResponseDto getInputNumbersFromUser(ReceiverRequestDto receiverRequestDto){
        return numberReceiverFacade.inputNumbers(receiverRequestDto.getUserTypedNumbers());
    }

}
