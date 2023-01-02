package com.synerset.lottery.infrastructure.winningnumberservice;

import com.synerset.lottery.infrastructure.objectmappers.JsonConverters;
import com.synerset.lottery.infrastructure.winningnumberservice.dto.WinningNumbersRequestDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

import java.time.LocalDateTime;

class HttpEntityGenerator {

    private final HttpHeaders headers;

    HttpEntityGenerator(HttpHeaders headers) {
        this.headers = headers;
    }

    HttpEntity<String> createHttpEntityWithBodyForDrawDate(LocalDateTime drawDate){
        WinningNumbersRequestDto requestDto = new WinningNumbersRequestDto(drawDate);
        String jsonDate = JsonConverters.writeObjectAsJsonString(requestDto);
        return new HttpEntity<>(jsonDate, headers);
    }

}
