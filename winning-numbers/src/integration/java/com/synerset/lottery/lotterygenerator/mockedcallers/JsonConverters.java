package com.synerset.lottery.lotterygenerator.mockedcallers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.synerset.lottery.lotterygenerator.winningnumbergenerator.dto.WinningNumbersRequestDto;
import com.synerset.lottery.lotterygenerator.winningnumbergenerator.dto.WinningNumbersResponseDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class JsonConverters {

    private final ObjectMapper objectMapper;

    public JsonConverters(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String convertDateTimeToWinningNumbersRequestBody(LocalDateTime drawDate) throws JsonProcessingException {
        WinningNumbersRequestDto winningNumbersRequestDto = new WinningNumbersRequestDto(drawDate);
        return objectMapper.writeValueAsString(winningNumbersRequestDto);
    }

    public WinningNumbersResponseDto convertJsonResponseToWinningNumbersDto(String responseAsJson){
        return convertJsonResponseToObject(responseAsJson,WinningNumbersResponseDto.class);
    }

    private  <K> K convertJsonResponseToObject(String responseAsJson, Class<K> objectClass){
        try {
            return objectMapper.readValue(responseAsJson,objectClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


}
