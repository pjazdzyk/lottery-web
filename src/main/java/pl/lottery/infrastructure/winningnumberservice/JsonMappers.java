package pl.lottery.infrastructure.winningnumberservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.lottery.infrastructure.winningnumberservice.dto.WinningNumbersRequestDto;

class JsonMappers {

    private final ObjectMapper objectMapper;

    JsonMappers(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    String winningNumbersRequestDtoAsJson(WinningNumbersRequestDto winningNumbersRequestDto) {
        try {
            return objectMapper.writeValueAsString(winningNumbersRequestDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
