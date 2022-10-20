package pl.lottery.infrastructure.winningnumberservice;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import pl.lottery.infrastructure.winningnumberservice.dto.WinningNumbersResponseDto;
import pl.lottery.resultschecker.WinningNumberGeneratorPort;

import java.time.LocalDateTime;
import java.util.List;

//TODO implement from HTTP service
public class WinningNumbersGeneratorPortHttpService implements WinningNumberGeneratorPort {

    private static final String SERVICE_URL = "http://ec2-35-158-119-20.eu-central-1.compute.amazonaws.com:8000/api/v1";
    private static final String GENERATE_URL = SERVICE_URL + "/generate";
    private static final String RETRIEVE_URL = SERVICE_URL + "/numbers";
    private static final String DELETE_URL = SERVICE_URL + "/delete";
    private static final String GET_ALL_URL = SERVICE_URL + "/all";

    private final RestTemplate restTemplate;
    private final HttpEntityGenerator httpEntityGenerator;

    public WinningNumbersGeneratorPortHttpService(RestTemplate restTemplate, HttpEntityGenerator httpEntityGenerator) {
        this.restTemplate = restTemplate;
        this.httpEntityGenerator = httpEntityGenerator;
    }

    @Override
    public WinningNumbersResponseDto generateWinningNumbers(LocalDateTime drawDate) {
        HttpEntity<String> requestWithDrawDate = httpEntityGenerator.createHttpEntityForDrawDate(drawDate);
        ResponseEntity<WinningNumbersResponseDto> responseEntity = restTemplate.postForEntity(GENERATE_URL, requestWithDrawDate, WinningNumbersResponseDto.class);
        return responseEntity.getBody();
    }

    @Override

    public WinningNumbersResponseDto retrieveWinningNumbersForDrawDate(LocalDateTime drawDate) {
        HttpEntity<String> requestWithDrawDate = httpEntityGenerator.createHttpEntityForDrawDate(drawDate);
        //TODO HOW???
        ResponseEntity<WinningNumbersResponseDto> responseEntity = restTemplate.getForEntity(RETRIEVE_URL, WinningNumbersResponseDto.class);
        return responseEntity.getBody();
    }

    @Override
    public WinningNumbersResponseDto deleteWinningNumbersForDate(LocalDateTime drawDate) {
        return null;
    }

    @Override
    public List<WinningNumbersResponseDto> getAllWinningNumbers() {
        return null;
    }

}
