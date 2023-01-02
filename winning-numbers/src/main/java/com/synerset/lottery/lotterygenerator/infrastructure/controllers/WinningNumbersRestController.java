package com.synerset.lottery.lotterygenerator.infrastructure.controllers;

import com.synerset.lottery.lotterygenerator.winningnumbergenerator.WinningNumberGeneratorFacade;
import com.synerset.lottery.lotterygenerator.winningnumbergenerator.dto.WinningNumbersRequestDto;
import com.synerset.lottery.lotterygenerator.winningnumbergenerator.dto.WinningNumbersResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class WinningNumbersRestController {

    private final WinningNumberGeneratorFacade winningNumberGeneratorFacade;

    public WinningNumbersRestController(WinningNumberGeneratorFacade winningNumberGeneratorFacade) {
        this.winningNumberGeneratorFacade = winningNumberGeneratorFacade;
    }

    @PostMapping(value="/api/v1/winning-numbers")
    public ResponseEntity<WinningNumbersResponseDto> generateWinningNumbersForDrawDate(@RequestBody WinningNumbersRequestDto winningNumbersRequestDto){
        LocalDateTime requestDrawDate = winningNumbersRequestDto.getDrawDate();
        WinningNumbersResponseDto generatedNumbersDto = winningNumberGeneratorFacade.generateWinningNumbers(requestDrawDate);
        return ResponseEntity.ok(generatedNumbersDto);
    }

    @GetMapping(value = "/api/v1/winning-numbers/{drawDateString}")
    public ResponseEntity<WinningNumbersResponseDto> getWinningNumbersForDrawDate(@PathVariable String drawDateString){
        LocalDateTime drawDate = LocalDateTime.parse(drawDateString);
        WinningNumbersResponseDto loadedFromDbNumbersDto = winningNumberGeneratorFacade.retrieveWinningNumbersForDrawDate(drawDate);
        return ResponseEntity.ok(loadedFromDbNumbersDto);
    }

    @GetMapping(value = "/api/v1/winning-numbers")
    public ResponseEntity<List<WinningNumbersResponseDto>> getAllWiningNumbers(){
        List<WinningNumbersResponseDto> allNumbersFromDbDto = winningNumberGeneratorFacade.getAllWinningNumbers();
        return ResponseEntity.ok(allNumbersFromDbDto);
    }

    @DeleteMapping(value = "/api/v1/winning-numbers")
    public ResponseEntity<WinningNumbersResponseDto> deleteWinningNumbersForDrawDate(@RequestParam("drawDate") String drawDateAsString){
        LocalDateTime drawDate = LocalDateTime.parse(drawDateAsString);
        WinningNumbersResponseDto deletedNumbersDto = winningNumberGeneratorFacade.deleteWinningNumbersForDate(drawDate);
        return ResponseEntity.ok(deletedNumbersDto);
    }

}
