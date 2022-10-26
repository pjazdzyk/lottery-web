package pl.lottery.infrastructure.winningnumberservice;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import pl.lottery.infrastructure.winningnumberservice.dto.WinningNumbersResponseDto;
import pl.lottery.resultschecker.WinningNumberGeneratorPort;

import java.time.LocalDateTime;
import java.util.List;

public class WinningNumbersGeneratorPortHttpService implements WinningNumberGeneratorPort {

    private final RestTemplate restTemplate;
    private final WinningNumberUrlGenerator winningNumberUrlGenerator;
    private final HttpEntityGenerator httpEntityGenerator;

    public WinningNumbersGeneratorPortHttpService(RestTemplate restTemplate, WinningNumberUrlGenerator winningNumberUrlGenerator, HttpEntityGenerator httpEntityGenerator) {
        this.restTemplate = restTemplate;
        this.winningNumberUrlGenerator = winningNumberUrlGenerator;
        this.httpEntityGenerator = httpEntityGenerator;
    }

    @Override
    public WinningNumbersResponseDto generateWinningNumbers(LocalDateTime drawDate) {
        String urlForGenerateCall = winningNumberUrlGenerator.createUrlForGenerateCall();
        HttpEntity<String> requestWithDrawDate = httpEntityGenerator.createHttpEntityWithBodyForDrawDate(drawDate);
        ResponseEntity<WinningNumbersResponseDto> responseEntity = restTemplate.postForEntity(urlForGenerateCall, requestWithDrawDate, WinningNumbersResponseDto.class);
        return responseEntity.getBody();
    }

    @Override

    public WinningNumbersResponseDto retrieveWinningNumbers(LocalDateTime drawDate) {
        String urlForRetrieveCall = winningNumberUrlGenerator.createUrlForRetrieveCall(drawDate);
        ResponseEntity<WinningNumbersResponseDto> responseEntity = restTemplate.getForEntity(urlForRetrieveCall, WinningNumbersResponseDto.class, drawDate.toString());
        return responseEntity.getBody();
    }

    @Override
    public WinningNumbersResponseDto deleteWinningNumbers(LocalDateTime drawDate) {
        return null;
    }

    @Override
    public List<WinningNumbersResponseDto> getAllWinningNumbers() {
        return null;
    }

}
