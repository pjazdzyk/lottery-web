package com.synerset.lottery.infrastructure.controllers.numberreceiver;

import com.synerset.lottery.numberreceiver.NumberReceiverFacade;
import com.synerset.lottery.numberreceiver.dto.ReceiverRequestDto;
import com.synerset.lottery.numberreceiver.dto.ReceiverResponseDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.List;

@Controller
public class NumberReceiverWebController {

    private final NumberReceiverFacade numberReceiverFacade;

    public NumberReceiverWebController(NumberReceiverFacade numberReceiverFacade) {
        this.numberReceiverFacade = numberReceiverFacade;
    }

    @GetMapping("/receiver")
    public String showReceiverView(){
        return "receiver";
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
