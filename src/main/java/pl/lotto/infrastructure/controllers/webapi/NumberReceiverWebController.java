package pl.lotto.infrastructure.controllers.webapi;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import pl.lotto.numberreceiver.NumberReceiverFacade;
import pl.lotto.numberreceiver.dto.ReceiverRequestDto;

@Controller
public class NumberReceiverWebController {

    private final NumberReceiverFacade numberReceiverFacade;

    public NumberReceiverWebController(NumberReceiverFacade numberReceiverFacade) {
        this.numberReceiverFacade = numberReceiverFacade;
    }

    @PostMapping("/receiver")
    public String getInputNumbersFromUser(ReceiverRequestDto receiverRequestDto) {
        numberReceiverFacade.inputNumbers(receiverRequestDto.getTypedNumbers());
        return "receiver-resp-view";
    }

}
