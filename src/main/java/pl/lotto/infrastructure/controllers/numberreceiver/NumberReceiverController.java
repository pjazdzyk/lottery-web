package pl.lotto.infrastructure.controllers.numberreceiver;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.lotto.numberreceiver.NumberReceiverFacade;
import pl.lotto.numberreceiver.dto.ReceiverRequestDto;
import pl.lotto.numberreceiver.dto.ReceiverResponseDto;

@RestController
class NumberReceiverController {

    private final NumberReceiverFacade numberReceiverFacade;

    NumberReceiverController(NumberReceiverFacade numberReceiverFacade) {
        this.numberReceiverFacade = numberReceiverFacade;
    }

    @GetMapping("/receiver")
    public ReceiverResponseDto inputNumbers(ReceiverRequestDto receiverRequestDto){
        return numberReceiverFacade.inputNumbers(receiverRequestDto.getUserTypedNumbers());
    }


}
