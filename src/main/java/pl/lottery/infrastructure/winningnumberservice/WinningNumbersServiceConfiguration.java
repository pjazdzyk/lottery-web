package pl.lottery.infrastructure.winningnumberservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

@Configuration
public class WinningNumbersServiceConfiguration {

    @Bean
    public WinningNumbersGeneratorHttpService createForProduction(ObjectMapper objectMapper){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JsonMappers jsonMappers = new JsonMappers(objectMapper);
        HttpEntityGenerator httpEntityGenerator = new HttpEntityGenerator(headers,jsonMappers);
        RestTemplate restTemplate = new RestTemplate();
        return new WinningNumbersGeneratorHttpService(restTemplate,httpEntityGenerator);
    }

}

