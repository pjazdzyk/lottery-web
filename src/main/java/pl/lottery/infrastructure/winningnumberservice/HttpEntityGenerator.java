package pl.lottery.infrastructure.winningnumberservice;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import pl.lottery.infrastructure.objectmappers.JsonConverters;
import pl.lottery.infrastructure.winningnumberservice.dto.WinningNumbersRequestDto;

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
