package pl.lotto.infrastructure.controllers.restapi;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.lotto.numberreceiver.NumberReceiverFacade;
import pl.lotto.numberreceiver.dto.ReceiverRequestDto;
import pl.lotto.numberreceiver.dto.ReceiverResponseDto;

@RestController
public class NumberReceiverRestController {

    private final NumberReceiverFacade numberReceiverFacade;

    public NumberReceiverRestController(NumberReceiverFacade numberReceiverFacade) {
        this.numberReceiverFacade = numberReceiverFacade;
    }

    @PostMapping(value = "/api/v1/receiver")
    public ResponseEntity<ReceiverResponseDto> inputNumbers(ReceiverRequestDto receiverRequestDto){
        ReceiverResponseDto receiverResponseDto = numberReceiverFacade.inputNumbers(receiverRequestDto.getUserTypedNumbers());
        return new ResponseEntity<>(receiverResponseDto, HttpStatus.OK);
    }

}
