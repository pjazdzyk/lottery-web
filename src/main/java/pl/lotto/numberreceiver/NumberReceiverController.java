package pl.lotto.numberreceiver;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.lotto.numberreceiver.dto.ReceiverDto;

import java.util.List;

@RestController
class NumberReceiverController {

    private final NumberReceiverFacade numberReceiverFacade;

    NumberReceiverController(NumberReceiverFacade numberReceiverFacade) {
        this.numberReceiverFacade = numberReceiverFacade;
    }

    @GetMapping("/receiver")
    ReceiverDto inputNumbers(@RequestParam(value = "typedNumbers") List<Integer> typedNumbers){
        return numberReceiverFacade.inputNumbers(typedNumbers);
    }


}
