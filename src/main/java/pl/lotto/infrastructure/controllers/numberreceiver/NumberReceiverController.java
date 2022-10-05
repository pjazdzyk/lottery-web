package pl.lotto.infrastructure.controllers.numberreceiver;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    ReceiverResponseDto inputNumbers(@RequestParam(value = "typedNumbers") ReceiverRequestDto requestDto){
        return numberReceiverFacade.inputNumbers(requestDto.typedNumbers());
    }


}
