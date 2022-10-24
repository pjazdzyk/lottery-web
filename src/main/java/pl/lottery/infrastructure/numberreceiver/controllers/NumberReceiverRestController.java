package pl.lottery.infrastructure.numberreceiver.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.lottery.infrastructure.numberreceiver.exceptions.ApiInvalidNumbersException;
import pl.lottery.numberreceiver.NumberReceiverFacade;
import pl.lottery.numberreceiver.dto.ReceiverRequestDto;
import pl.lottery.numberreceiver.dto.ReceiverResponseDto;

import java.util.List;

@RestController
public class NumberReceiverRestController {

    private final NumberReceiverFacade numberReceiverFacade;
    private static final String INVALID_NUMBERS_MSG = "Invalid numbers. Please check number constraint rules and try again.";

    public NumberReceiverRestController(NumberReceiverFacade numberReceiverFacade) {
        this.numberReceiverFacade = numberReceiverFacade;
    }

    @PostMapping(value = "/api/v1/receiver")
    public ResponseEntity<ReceiverResponseDto> inputNumbers(@RequestBody ReceiverRequestDto receiverRequestDto) {
        List<Integer> typedNumbers = receiverRequestDto.getTypedNumbers();
        if (numberReceiverFacade.numbersAreNotValid(typedNumbers)) {
            throw new ApiInvalidNumbersException(INVALID_NUMBERS_MSG);
        }
        ReceiverResponseDto receiverResponseDto = numberReceiverFacade.inputNumbers(typedNumbers);
        return new ResponseEntity<>(receiverResponseDto, HttpStatus.OK);
    }

}
