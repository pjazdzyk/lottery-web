package pl.lottery.infrastructure.winningnumberservice;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import pl.lottery.infrastructure.winningnumberservice.dto.WinningNumbersRequestDto;

import java.time.LocalDateTime;

class HttpEntityGenerator {

    private final HttpHeaders headers;
    private final JsonMappers jsonMappers;

    HttpEntityGenerator(HttpHeaders headers, JsonMappers jsonMappers) {
        this.headers = headers;
        this.jsonMappers = jsonMappers;
    }

    HttpEntity<String> createHttpEntityWithBodyForDrawDate(LocalDateTime drawDate){
        WinningNumbersRequestDto requestDto = new WinningNumbersRequestDto(drawDate);
        String jsonDate = jsonMappers.winningNumbersRequestDtoAsJson(requestDto);
        return new HttpEntity<>(jsonDate, headers);
    }

}
