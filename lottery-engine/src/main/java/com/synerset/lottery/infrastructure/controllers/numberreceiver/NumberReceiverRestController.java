package com.synerset.lottery.infrastructure.controllers.numberreceiver;

import com.synerset.lottery.infrastructure.exceptions.numberreceiver.ApiInvalidNumbersException;
import com.synerset.lottery.numberreceiver.NumberReceiverFacade;
import com.synerset.lottery.numberreceiver.dto.ReceiverRequestDto;
import com.synerset.lottery.numberreceiver.dto.ReceiverResponseDto;
import com.synerset.lottery.numberreceiver.dto.ReceiverStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NumberReceiverRestController {

    private static final String INVALID_NUMBERS_MSG = "Invalid numbers. Please check number constraint rules and try again.";
    private final NumberReceiverFacade numberReceiverFacade;

    public NumberReceiverRestController(NumberReceiverFacade numberReceiverFacade) {
        this.numberReceiverFacade = numberReceiverFacade;
    }

    @PostMapping(value = ReceiverEndpointVersions.API_VERSION_V1 + "/numbers")
    public ResponseEntity<ReceiverResponseDto> inputNumbers(@RequestBody ReceiverRequestDto receiverRequestDto) {
        List<Integer> typedNumbers = receiverRequestDto.getTypedNumbers();
        if (numberReceiverFacade.numbersAreNotValid(typedNumbers)) {
            throw new ApiInvalidNumbersException(INVALID_NUMBERS_MSG);
        }
        ReceiverResponseDto receiverResponseDto = numberReceiverFacade.inputNumbers(typedNumbers);
        HttpStatus httpStatus = receiverResponseDto.status() == ReceiverStatus.NOT_FOUND
                ? HttpStatus.NOT_FOUND
                : HttpStatus.OK;
        return new ResponseEntity<>(receiverResponseDto, httpStatus);
    }
}
